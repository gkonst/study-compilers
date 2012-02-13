package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class AssignNode extends Node {
    private VariableNode variable;
    private Node value;

    public AssignNode(VariableNode variable, Node value) {
        super(NodeType.ASSIGN);
        this.variable = variable;
        this.value = value;
    }

    public VariableNode getVariable() {
        return variable;
    }

    public Node getValue() {
        return value;
    }
}
