package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class LTNode extends BinaryOperation {
    protected LTNode(Node left, Node right) {
        super(NodeType.LT, left, right);
    }
}
