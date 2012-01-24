package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class SubNode extends BinaryOperation {
    protected SubNode(Node left, Node right) {
        super(NodeType.SUB, left, right);
    }
}
