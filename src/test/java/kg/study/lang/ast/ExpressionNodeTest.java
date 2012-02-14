package kg.study.lang.ast;

import static kg.study.lang.ast.ASTAssert.assertConstNode;
import static kg.study.lang.ast.ASTAssert.assertVariableNode;
import static org.testng.Assert.assertEquals;

import kg.study.lang.NodeType;
import kg.study.lang.ParserContext;
import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

public class ExpressionNodeTest {
    @Test
    public void parseShouldWorkIfIdentifierGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("a if");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertEquals(node.getType(), NodeType.VARIABLE);
        assertEquals(((VariableNode) node).getName(), "a");
    }

    @Test
    public void parseShouldWorkIfValueGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("1 \nwhile");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertConstNode(node, 1);
    }

    @Test
    public void parseShouldWorkIfArithmeticOperationGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("a + 1 \nif");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertEquals(node.getType(), NodeType.ADD);
        AddNode addNode = (AddNode) node;
        assertVariableNode(addNode.getLeft(), "a");
        assertConstNode(addNode.getRight(), 1);
    }

    @Test
    public void parseShouldWorkIfTwoArithmeticOperationGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("a + 1 + b \nif");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertEquals(node.getType(), NodeType.ADD);
        AddNode addNode = (AddNode) node;
        assertVariableNode(addNode.getLeft(), "a");
        AddNode addNode1 = (AddNode) addNode.getRight();
        assertConstNode(addNode1.getLeft(), 1);
        assertVariableNode(addNode1.getRight(), "b");
    }

    @Test
    public void parseShouldWorkIfArithmeticOperationsWithParensGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("5 + (6 - 1) \nif");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertEquals(node.getType(), NodeType.ADD);
        AddNode addNode = (AddNode) node;
        assertConstNode(addNode.getLeft(), 5);
        assertEquals(addNode.getRight().getType(), NodeType.SUB);
        SubNode subNode = (SubNode) addNode.getRight();
        assertConstNode(subNode.getLeft(), 6);
        assertConstNode(subNode.getRight(), 1);
    }

    @Test
    public void parseShouldWorkIfArithmeticAndCompareOperationsGiven() throws Exception {
        // given
        Lexer lexer = Lexer.forString("(a + 1) < (10 - 5) \nif");
        // when
        Node node = ExpressionNode.parse(new ParserContext(lexer));
        // then
        assertEquals(node.getType(), NodeType.LT);
        LTNode ltNode = (LTNode) node;
        assertEquals(ltNode.getLeft().getType(), NodeType.ADD);
        AddNode addNode = (AddNode) ltNode.getLeft();
        assertVariableNode(addNode.getLeft(), "a");
        assertConstNode(addNode.getRight(), 1);
        assertEquals(ltNode.getRight().getType(), NodeType.SUB);
        SubNode subNode = (SubNode) ltNode.getRight();
        assertConstNode(subNode.getLeft(), 10);
        assertConstNode(subNode.getRight(), 5);
    }
}
