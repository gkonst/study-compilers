package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class ProgramNode extends Node {
    private SeqNode seqNode;

    public ProgramNode(SeqNode seqNode) {
        super(NodeType.PROGRAM);
        this.seqNode = seqNode;
    }

    public SeqNode getSeqNode() {
        return seqNode;
    }
}
