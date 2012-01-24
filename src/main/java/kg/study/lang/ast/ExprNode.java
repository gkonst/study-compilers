package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class ExprNode extends Node {
    private Node child;

    public ExprNode(Node child) {
        super(NodeType.EXPR);
        this.child = child;
    }

    public Node getChild() {
        return child;
    }
}
