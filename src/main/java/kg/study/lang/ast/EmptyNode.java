package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class EmptyNode extends Node {
    protected EmptyNode() {
        super(NodeType.EMPTY);
    }
}
