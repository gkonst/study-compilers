package kg.study.lang.parser;


import kg.study.lang.ast.ASTMatchers
import kg.study.lang.ast.AssignNode
import kg.study.lang.ast.ConstNode
import kg.study.lang.ast.EmptyNode
import kg.study.lang.ast.ExpressionNode
import kg.study.lang.ast.IfNode
import kg.study.lang.ast.LTNode
import kg.study.lang.ast.Node
import kg.study.lang.ast.PrintNode
import kg.study.lang.ast.ProgramNode
import kg.study.lang.ast.SeqNode
import kg.study.lang.ast.VariableNode
import kg.study.lang.lexer.Lexer
import spock.lang.Specification

class PredictiveParserSpec extends Specification {

    def setupSpec() {
        Node.metaClass.mixin(ASTMatchers)
    }

    def cleanupSpec() {
        Node.metaClass = null
    }

    def "parse should work"() {
        given:
        def given = ' { a = 3; if (a < 0) a = 5; print(a);}'
        def parser = new PredictiveParser(Lexer.forString(given))

        when:
        def result = parser.parse()

        then:
        result.shouldBe ProgramNode, {
            seqNode.shouldBe SeqNode, {
                next().shouldBe ExpressionNode, {
                    child.shouldBe AssignNode, {
                        variable.shouldBe VariableNode, 'a'
                        value.shouldBe ConstNode, 3
                    }
                }
                next().shouldBe IfNode, {
                    condition.shouldBe LTNode, {
                        left.shouldBe VariableNode, 'a'
                        right.shouldBe ConstNode, 0
                    }
                    body.shouldBe ExpressionNode, {
                        child.shouldBe AssignNode, {
                            variable.shouldBe VariableNode, 'a'
                            value.shouldBe ConstNode, 5
                        }
                    }
                }
                next().shouldBe PrintNode, {
                    variable.shouldBe VariableNode, 'a'
                }
                next().shouldBe EmptyNode
            }
        }
    }
}
