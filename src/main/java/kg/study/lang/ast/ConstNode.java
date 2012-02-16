package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class ConstNode extends Node {
    private final int value;

    protected ConstNode(int value) {
        super(NodeType.CONST);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getType() + "{" + value + '}';
    }
}
