package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class WhileNode extends ConditionNode {
    public WhileNode(BinaryOperation condition, Node body) {
        super(NodeType.WHILE, condition, body);
    }
}
