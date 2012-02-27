package kg.study.lang.ast;

public class SumNode extends BinaryOperation {
    protected SumNode(Node left, Node right) {
        super(NodeType.SUM, left, right);
    }
}
