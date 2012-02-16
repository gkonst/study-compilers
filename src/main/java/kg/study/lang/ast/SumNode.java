package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class SumNode extends BinaryOperation {
    protected SumNode(Node left, Node right) {
        super(NodeType.SUM, left, right);
    }
}
