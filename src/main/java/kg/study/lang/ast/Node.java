package kg.study.lang.ast;

public abstract class Node {
    private final NodeType type;

    protected Node(NodeType type) {
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }
}
