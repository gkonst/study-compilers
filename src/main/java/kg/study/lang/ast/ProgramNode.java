package kg.study.lang.ast;

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
