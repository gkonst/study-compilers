package kg.study.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertEquals;

import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

public class ParserTest {

    public static final int A_CODE = 0;

    @Test
    public void parseShouldNotFail() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; }";
        Parser parser = new Parser(new Lexer(given));
        // when
        Node result = parser.parse();
        // then
        assertEquals(result.getType(), Node.NodeType.PROGRAM);
        assertThat(result.getChildren(), hasSize(1));
        Node seqNode = result.getChildren().get(0);
        assertSeqNode(seqNode, 2);
        //  assert inner seq node
        Node innerSeqNode = seqNode.getChildren().get(0);
        assertSeqNode(innerSeqNode, 2);
        assertEmptyNode(innerSeqNode.getChildren().get(0));
        Node exprNode = innerSeqNode.getChildren().get(1);
        assertExprNode(exprNode, A_CODE, 3);
        //  assert if node
        Node ifNode = seqNode.getChildren().get(1);
        assertEquals(ifNode.getType(), Node.NodeType.IF);
        assertThat(ifNode.getChildren(), hasSize(2));
        //   assert lt node
        Node ltNode = ifNode.getChildren().get(0);
        assertEquals(ltNode.getType(), Node.NodeType.LT);
        assertThat(ltNode.getChildren(), hasSize(2));
        assertVarNode(ltNode.getChildren().get(0), A_CODE);
        assertConstNode(ltNode.getChildren().get(1), 0);
        //   assert expr node
        exprNode = ifNode.getChildren().get(1);
        assertExprNode(exprNode, A_CODE, 5);
    }

    private static void assertEmptyNode(Node emptyNode) {
        assertEquals(emptyNode.getType(), Node.NodeType.EMPTY);
        assertThat(emptyNode.getChildren(), is(nullValue()));
    }

    private static void assertExprNode(Node exprNode, int code, int value) {
        assertEquals(exprNode.getType(), Node.NodeType.EXPR);
        assertThat(exprNode.getChildren(), hasSize(1));
        Node setNode = exprNode.getChildren().get(0);
        assertEquals(setNode.getType(), Node.NodeType.SET);
        assertThat(setNode.getChildren(), hasSize(2));
        assertVarNode(setNode.getChildren().get(0), code);
        assertConstNode(setNode.getChildren().get(1), value);
    }

    private static void assertSeqNode(Node seqNode, int size) {
        assertEquals(seqNode.getType(), Node.NodeType.SEQ);
        assertThat(seqNode.getChildren(), hasSize(size));
    }

    private static void assertConstNode(Node constNode, int value) {
        assertEquals(constNode.getType(), Node.NodeType.CONST);
        assertEquals(constNode.getValue(), value);
        assertThat(constNode.getChildren(), is(nullValue()));
    }

    private static void assertVarNode(Node varNode, int value) {
        assertEquals(varNode.getType(), Node.NodeType.VAR);
        assertEquals(varNode.getValue(), value);
        assertThat(varNode.getChildren(), is(nullValue()));
    }
}
