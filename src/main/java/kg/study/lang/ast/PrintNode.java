package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class PrintNode extends Node {
    private VarNode variable;

    public PrintNode(VarNode variable) {
        super(NodeType.PRINT);
        this.variable = variable;
    }

    public VarNode getVariable() {
        return variable;
    }
}
