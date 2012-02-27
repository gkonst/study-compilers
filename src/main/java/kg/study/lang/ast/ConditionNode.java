package kg.study.lang.ast;

public abstract class ConditionNode extends Node {
    protected BinaryOperation condition;
    protected Node body;

    protected ConditionNode(NodeType type, BinaryOperation condition, Node body) {
        super(type);
        this.condition = condition;
        this.body = body;
    }

    public BinaryOperation getCondition() {
        return condition;
    }

    public Node getBody() {
        return body;
    }
}
