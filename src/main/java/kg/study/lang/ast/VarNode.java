package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class VarNode extends Node {

    private final int name;

    protected VarNode(int name) {
        super(NodeType.VAR);
        this.name = name;
    }

    public int getName() {
        return name;
    }
}
