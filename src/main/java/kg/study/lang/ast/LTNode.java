package kg.study.lang.ast;

public class LTNode extends BinaryOperation {
    protected LTNode(Node left, Node right) {
        super(NodeType.LT, left, right);
    }
}
