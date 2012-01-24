package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class AddNode extends BinaryOperation {
    protected AddNode(Node left, Node right) {
        super(NodeType.ADD, left, right);
    }
}
