package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class PrintNode extends Node {
    private VariableNode variable;

    public PrintNode(VariableNode variable) {
        super(NodeType.PRINT);
        this.variable = variable;
    }

    public VariableNode getVariable() {
        return variable;
    }
}
