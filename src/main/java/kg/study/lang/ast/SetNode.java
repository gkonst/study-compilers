package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class SetNode extends Node {
    private VarNode variable;
    private Node value;

    public SetNode(VarNode variable, Node value) {
        super(NodeType.SET);
        this.variable = variable;
        this.value = value;
    }

    public VarNode getVariable() {
        return variable;
    }

    public Node getValue() {
        return value;
    }
}
