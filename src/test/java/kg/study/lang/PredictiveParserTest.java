package kg.study.lang;

import static kg.study.lang.ast.ASTAssert.assertConstNode;
import static kg.study.lang.ast.ASTAssert.assertEmptyNode;
import static kg.study.lang.ast.ASTAssert.assertExprNode;
import static kg.study.lang.ast.ASTAssert.assertPrintNode;
import static kg.study.lang.ast.ASTAssert.assertSeqNode;
import static kg.study.lang.ast.ASTAssert.assertVariableNode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import kg.study.lang.ast.ExpressionNode;
import kg.study.lang.ast.IfNode;
import kg.study.lang.ast.LTNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

import java.util.Iterator;

public class PredictiveParserTest {

    @Test
    public void parseShouldNotFail() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; print(a);}";
        PredictiveParser parser = new PredictiveParser(Lexer.forString(given));
        // when
        Node result = parser.parse();
        // then
        System.out.println(result);
        assertEquals(result.getType(), NodeType.PROGRAM);
        assertThat(((ProgramNode) result).getSeqNode(), is(notNullValue()));
        SeqNode seqNode = ((ProgramNode) result).getSeqNode();
        assertSeqNode(seqNode, 4);
        //  assert inner seq node
        Iterator<Node> seqIterator = seqNode.getChildren().iterator();
        ExpressionNode exprNode = (ExpressionNode) seqIterator.next();
        assertExprNode(exprNode, "a", 3);
        //  assert if node
        IfNode ifNode = (IfNode) seqIterator.next();
        assertEquals(ifNode.getType(), NodeType.IF);
        //   assert lt node
        LTNode ltNode = (LTNode) ifNode.getCondition();
        assertEquals(ltNode.getType(), NodeType.LT);
        assertVariableNode(ltNode.getLeft(), "a");
        assertConstNode(ltNode.getRight(), 0);
        //   assert expr node
        exprNode = (ExpressionNode) ifNode.getBody();
        assertExprNode(exprNode, "a", 5);
        PrintNode printNode = (PrintNode) seqIterator.next();
        assertPrintNode(printNode, "a");
        assertEmptyNode(seqIterator.next());
    }
}
