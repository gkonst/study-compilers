package kg.study.lang.ast;

public class IfElseNode extends ConditionNode {
    private Node elseBody;

    public IfElseNode(BinaryOperation condition, Node ifBody, Node elseBody) {
        super(NodeType.IFELSE, condition, ifBody);
        this.elseBody = elseBody;
    }

    public Node getElseBody() {
        return elseBody;
    }
}
