package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class VarNode extends Node {

    private final String name;

    protected VarNode(String name) {
        super(NodeType.VAR);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
