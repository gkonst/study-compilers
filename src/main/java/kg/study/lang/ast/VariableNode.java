package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class VariableNode extends Node {

    private final String name;

    protected VariableNode(String name) {
        super(NodeType.VARIABLE);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
