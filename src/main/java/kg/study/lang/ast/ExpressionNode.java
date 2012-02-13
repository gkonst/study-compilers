package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class ExpressionNode extends Node {
    private Node child;

    public ExpressionNode(Node child) {
        super(NodeType.EXPRESSION);
        this.child = child;
    }

    public Node getChild() {
        return child;
    }
}
