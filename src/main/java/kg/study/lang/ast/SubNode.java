package kg.study.lang.ast;

public class SubNode extends BinaryOperation {
    protected SubNode(Node left, Node right) {
        super(NodeType.SUB, left, right);
    }
}
