package kg.study.lang.ast;

import kg.study.lang.NodeType;

public abstract class BinaryOperation extends Node {

    protected Node left;
    protected Node right;

    protected BinaryOperation(NodeType type, Node left, Node right) {
        super(type);
        this.right = right;
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
