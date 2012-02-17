package kg.study.lang.ast;


import kg.study.lang.ParserContext
import kg.study.lang.lexer.Lexer
import spock.lang.Specification

class ExpressionNodeSpec extends Specification {

    def setupSpec() {
        Node.metaClass.mixin(ASTMatchers)
    }

    def cleanupSpec() {
        Node.metaClass = null
    }

    void "parse should work if identifier is given"() {
        expect:
        treeFor 'a if' shouldBe VariableNode, 'a'
    }

    void "parse should work if value is given"() {
        expect:
        treeFor '1 \nwhile' shouldBe ConstNode, 1
    }

    void "parse should work if arithmetic operation is given"() {
        expect:
        treeFor 'a + 1 \nif' shouldBe SumNode, {
            left.shouldBeVariable 'a'
            right.shouldBeConst 1
        }
    }

    void "parse should work if two arithmetic operations are given"() {
        expect:
        treeFor 'a + 1 + b \nif' shouldBe SumNode, {
            left.shouldBeVariable 'a'
            right.shouldBe SumNode, {
                left.shouldBeConst 1
                right.shouldBeVariable 'b'
            }
        }
    }

    void "parse should work if arithmetic operations with parens are given"() {
        expect:
        treeFor '5 + (6 - 1) \nif' shouldBe SumNode, {
            left.shouldBeConst 5
            right.shouldBe SubNode, {
                left.shouldBeConst 6
                right.shouldBeConst 1
            }
        }
    }

    void "parse should work if arithmetic and compare operations are given"() {
        expect:
        treeFor '(a + 1) < (10 - 5) \nif' shouldBe LTNode, {
            left.shouldBe SumNode, {
                left.shouldBeVariable 'a'
                right.shouldBeConst 1
            }
            right.shouldBe SubNode, {
                left.shouldBeConst 10
                right.shouldBeConst 5
            }
        }
    }

    private static Node treeFor(input) {
        ExpressionNode.parse(new ParserContext(Lexer.forString(input)))
    }
}
