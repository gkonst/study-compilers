package kg.study.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.ExprNode;
import kg.study.lang.ast.IfNode;
import kg.study.lang.ast.LTNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.SetNode;
import kg.study.lang.ast.VarNode;
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
        ExprNode exprNode = (ExprNode) seqIterator.next();
        assertExprNode(exprNode, "a", 3);
        //  assert if node
        IfNode ifNode = (IfNode) seqIterator.next();
        assertEquals(ifNode.getType(), NodeType.IF);
        //   assert lt node
        LTNode ltNode = (LTNode) ifNode.getCondition();
        assertEquals(ltNode.getType(), NodeType.LT);
        assertVarNode((VarNode) ltNode.getLeft(), "a");
        assertConstNode((ConstNode) ltNode.getRight(), 0);
        //   assert expr node
        exprNode = (ExprNode) ifNode.getBody();
        assertExprNode(exprNode, "a", 5);
        PrintNode printNode = (PrintNode) seqIterator.next();
        assertPrintNode(printNode, "a");
        assertEmptyNode(seqIterator.next());
    }

    private static void assertPrintNode(PrintNode printNode, String name) {
        assertEquals(printNode.getType(), NodeType.PRINT);
        assertVarNode(printNode.getVariable(), name);
    }

    private static void assertEmptyNode(Node emptyNode) {
        assertEquals(emptyNode.getType(), NodeType.EMPTY);
    }

    private static void assertExprNode(ExprNode exprNode, String name, int value) {
        assertEquals(exprNode.getType(), NodeType.EXPR);
        assertThat(exprNode.getChild(), is(notNullValue()));
        SetNode setNode = (SetNode) exprNode.getChild();
        assertEquals(setNode.getType(), NodeType.SET);
        assertVarNode(setNode.getVariable(), name);
        assertConstNode((ConstNode) setNode.getValue(), value);
    }

    private static void assertSeqNode(SeqNode seqNode, int size) {
        assertEquals(seqNode.getType(), NodeType.SEQ);
        assertThat(seqNode.getChildren(), hasSize(size));
    }

    private static void assertConstNode(ConstNode constNode, int value) {
        assertEquals(constNode.getType(), NodeType.CONST);
        assertEquals(constNode.getValue(), value);
    }

    private static void assertVarNode(VarNode varNode, String name) {
        assertEquals(varNode.getType(), NodeType.VAR);
        assertEquals(varNode.getName(), name);
    }
}
