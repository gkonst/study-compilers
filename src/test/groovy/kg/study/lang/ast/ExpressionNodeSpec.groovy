package kg.study.lang.ast;


import kg.study.lang.ParserContext
import kg.study.lang.lexer.Lexer
import spock.lang.Specification

class ExpressionNodeSpec extends Specification {

    def setupSpec() {
        Node.metaClass.mixin(ASTAssert)
    }

    def cleanupSpec() {
        Node.metaClass = null
    }

    void "parse should work if identifier is given"() {
        expect:
        treeFor 'a if' shouldBeVariable 'a'
    }

    void "parse should work if value is given"() {
        expect:
        treeFor '1 \nwhile' shouldBeConstant 1
    }

    void "parse should work if arithmetic operation is given"() {
        expect:
        treeFor 'a + 1 \nif' shouldBeSum {
            left.shouldBeVariable 'a'
            right.shouldBeConstant 1
        }
    }

    void "parse should work if two arithmetic operations are given"() {
        expect:
        treeFor 'a + 1 + b \nif' shouldBeSum {
            left.shouldBeVariable 'a'
            right.shouldBeSum {
                left.shouldBeConstant 1
                right.shouldBeVariable 'b'
            }
        }
    }

    void "parse should work if arithmetic operations with parens are given"() {
        expect:
        treeFor '5 + (6 - 1) \nif' shouldBeSum {
            left.shouldBeConstant 5
            right.shouldBeSub {
                left.shouldBeConstant 6
                right.shouldBeConstant 1
            }
        }
    }

    void "parse should work if arithmetic and compare operations are given"() {
        expect:
        treeFor '(a + 1) < (10 - 5) \nif' shouldBeLT {
            left.shouldBeSum {
                left.shouldBeVariable 'a'
                right.shouldBeConstant 1
            }
            right.shouldBeSub {
                left.shouldBeConstant 10
                right.shouldBeConstant 5
            }
        }
    }

    private static Node treeFor(input) {
        ExpressionNode.parse(new ParserContext(Lexer.forString(input)))
    }
}
