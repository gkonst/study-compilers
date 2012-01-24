package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class IfNode extends ConditionNode {

    public IfNode(BinaryOperation condition, Node body) {
        super(NodeType.IF, condition, body);
    }

}
