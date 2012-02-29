package kg.study.lang.ast;

import kg.study.lang.parser.VariablesTable;

public class ProgramNode extends Node {
    private SeqNode seqNode;
    private VariablesTable variablesTable;

    public ProgramNode(SeqNode seqNode, VariablesTable variablesTable) {
        super(NodeType.PROGRAM);
        this.seqNode = seqNode;
        this.variablesTable = variablesTable;
    }

    public SeqNode getSeqNode() {
        return seqNode;
    }

    public VariablesTable getVariablesTable() {
        return variablesTable;
    }
}
