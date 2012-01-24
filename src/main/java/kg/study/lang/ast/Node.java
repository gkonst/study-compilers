package kg.study.lang.ast;

import kg.study.lang.NodeType;

public abstract class Node {
    private final NodeType type;

    protected Node(NodeType type) {
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }
}
