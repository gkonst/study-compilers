package kg.study.lang.ast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;

import kg.study.lang.NodeType;
import org.testng.Assert;

public final class ASTAssert {

    private ASTAssert() {
    }

    public static void assertPrintNode(PrintNode printNode, String name) {
        Assert.assertEquals(printNode.getType(), NodeType.PRINT);
        assertVariableNode(printNode.getVariable(), name);
    }

    public static void assertEmptyNode(Node emptyNode) {
        assertEquals(emptyNode.getType(), NodeType.EMPTY);
    }

    public static void assertExprNode(ExpressionNode exprNode, String name, int value) {
        assertEquals(exprNode.getType(), NodeType.EXPRESSION);
        assertThat(exprNode.getChild(), is(notNullValue()));
        AssignNode assignNode = (AssignNode) exprNode.getChild();
        assertEquals(assignNode.getType(), NodeType.ASSIGN);
        assertVariableNode(assignNode.getVariable(), name);
        assertConstNode(assignNode.getValue(), value);
    }

    public static void assertSeqNode(SeqNode seqNode, int size) {
        assertEquals(seqNode.getType(), NodeType.SEQ);
        assertThat(seqNode.getChildren(), hasSize(size));
    }

    public static void assertConstNode(Node constNode, int value) {
        assertEquals(constNode.getType(), NodeType.CONST);
        assertEquals(((ConstNode) constNode).getValue(), value);
    }

    public static void assertVariableNode(Node varNode, String name) {
        assertEquals(varNode.getType(), NodeType.VARIABLE);
        assertEquals(((VariableNode) varNode).getName(), name);
    }
}
