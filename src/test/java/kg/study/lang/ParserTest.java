package kg.study.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertEquals;

import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

public class ParserTest {

    @Test
    public void parseShouldNotFail() throws Exception {
        // given
        String given = "if (a < 0) a = 5;";
        Parser parser = new Parser(new Lexer(given));
        // when
        Node result = parser.parse();
        // then
        assertEquals(result.getType(), Node.NodeType.PROGRAM);
        assertThat(result.getChildren(), hasSize(1));
        Node ifNode = result.getChildren().get(0);
        assertEquals(ifNode.getType(), Node.NodeType.IF);
        assertThat(ifNode.getChildren(), hasSize(2));
        //  assert lt node
        Node ltNode = ifNode.getChildren().get(0);
        assertEquals(ltNode.getType(), Node.NodeType.LT);
        assertThat(ltNode.getChildren(), hasSize(2));
        assertVarNode(ltNode.getChildren().get(0), 0);
        assertConstNode(ltNode.getChildren().get(1), 0);
        //  assert expr node
        Node exprNode = ifNode.getChildren().get(1);
        assertEquals(exprNode.getType(), Node.NodeType.EXPR);
        assertThat(exprNode.getChildren(), hasSize(1));
        Node setNode = exprNode.getChildren().get(0);
        assertEquals(setNode.getType(), Node.NodeType.SET);
        assertThat(setNode.getChildren(), hasSize(2));
        assertVarNode(setNode.getChildren().get(0), 0);
        assertConstNode(setNode.getChildren().get(1), 5);
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
