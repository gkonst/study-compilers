package kg.study.lang.ast;

import kg.study.lang.NodeType;

public class DoNode extends ConditionNode {
    protected DoNode(BinaryOperation condition, Node body) {
        super(NodeType.DO, condition, body);
    }
}
