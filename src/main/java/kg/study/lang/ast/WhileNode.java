package kg.study.lang.ast;

public class WhileNode extends ConditionNode {
    public WhileNode(BinaryOperation condition, Node body) {
        super(NodeType.WHILE, condition, body);
    }
}
