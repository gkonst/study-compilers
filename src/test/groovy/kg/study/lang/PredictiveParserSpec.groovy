package kg.study.lang;


import kg.study.lang.ast.IfNode
import kg.study.lang.ast.LTNode
import kg.study.lang.ast.ProgramNode
import kg.study.lang.lexer.Lexer
import spock.lang.Specification
import static kg.study.lang.ast.ASTAssert.assertEmptyNode
import static kg.study.lang.ast.ASTAssert.assertExprNode
import static kg.study.lang.ast.ASTAssert.assertPrintNode
import static kg.study.lang.ast.ASTAssert.assertSeqNode
import static kg.study.lang.ast.ASTAssert.shouldBeConstant
import static kg.study.lang.ast.ASTAssert.shouldBeVariable

class PredictiveParserSpec extends Specification {

    def "parse should work"() {
        given:
        def given = ' { a = 3; if (a < 0) a = 5; print(a);}'
        def parser = new PredictiveParser(Lexer.forString(given))

        when:
        def result = parser.parse()

        then:
        assert result instanceof ProgramNode
        assert result.type == NodeType.PROGRAM
        assert result.seqNode != null
        assertSeqNode result.seqNode, 4
        //  assert inner seq node
        def seqIterator = result.seqNode.children.iterator()
        assertExprNode seqIterator.next(), 'a', 3
        //  assert if node
        def ifNode = seqIterator.next() as IfNode
        ifNode.type == NodeType.IF
        //   assert lt node
        assert ifNode.condition instanceof LTNode
        assert ifNode.condition.type == NodeType.LT
        shouldBeVariable ifNode.condition.left, 'a'
        shouldBeConstant ifNode.condition.right, 0
        //   assert expr node
        assertExprNode ifNode.body, 'a', 5
        assertPrintNode seqIterator.next(), 'a'
        assertEmptyNode seqIterator.next()
    }
}
