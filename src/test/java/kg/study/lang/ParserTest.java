package kg.study.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

public class ParserTest {

    public static final int A_CODE = 0;

    @Test
    public void parseShouldNotFail() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; print(a);}";
        Parser parser = new Parser(new Lexer(given));
        // when
        Node result = parser.parse();
        // then
        System.out.println(result);
        assertEquals(result.getType(), NodeType.PROGRAM);
        assertThat(result.getChildren(), hasSize(1));
        Node seqNode = result.getChildren().get(0);
        assertSeqNode(seqNode, 4);
        //  assert inner seq node
        Node exprNode = seqNode.getChildren().get(0);
        assertExprNode(exprNode, A_CODE, 3);
        //  assert if node
        Node ifNode = seqNode.getChildren().get(1);
        assertEquals(ifNode.getType(), NodeType.IF);
        assertThat(ifNode.getChildren(), hasSize(2));
        //   assert lt node
        Node ltNode = ifNode.getChildren().get(0);
        assertEquals(ltNode.getType(), NodeType.LT);
        assertThat(ltNode.getChildren(), hasSize(2));
        assertVarNode(ltNode.getChildren().get(0), A_CODE);
        assertConstNode(ltNode.getChildren().get(1), 0);
        //   assert expr node
        exprNode = ifNode.getChildren().get(1);
        assertExprNode(exprNode, A_CODE, 5);
        Node printNode = seqNode.getChildren().get(2);
        assertPrintNode(printNode, A_CODE);
        assertEmptyNode(seqNode.getChildren().get(3));
    }

    private static void assertPrintNode(Node printNode, int code) {
        assertEquals(printNode.getType(), NodeType.PRINT);
        assertThat(printNode.getChildren(), hasSize(1));
        assertVarNode(printNode.getChildren().get(0), code);
    }

    private static void assertEmptyNode(Node emptyNode) {
        assertEquals(emptyNode.getType(), NodeType.EMPTY);
        assertThat(emptyNode.getChildren(), hasSize(0));
    }

    private static void assertExprNode(Node exprNode, int code, int value) {
        assertEquals(exprNode.getType(), NodeType.EXPR);
        assertThat(exprNode.getChildren(), hasSize(1));
        Node setNode = exprNode.getChildren().get(0);
        assertEquals(setNode.getType(), NodeType.SET);
        assertThat(setNode.getChildren(), hasSize(2));
        assertVarNode(setNode.getChildren().get(0), code);
        assertConstNode(setNode.getChildren().get(1), value);
    }

    private static void assertSeqNode(Node seqNode, int size) {
        assertEquals(seqNode.getType(), NodeType.SEQ);
        assertThat(seqNode.getChildren(), hasSize(size));
    }

    private static void assertConstNode(Node constNode, int value) {
        assertEquals(constNode.getType(), NodeType.CONST);
        assertEquals(constNode.getValue(), value);
        assertThat(constNode.getChildren(), hasSize(0));
    }

    private static void assertVarNode(Node varNode, int code) {
        assertEquals(varNode.getType(), NodeType.VAR);
        assertEquals(varNode.getValue(), code);
        assertThat(varNode.getChildren(), hasSize(0));
    }
}
