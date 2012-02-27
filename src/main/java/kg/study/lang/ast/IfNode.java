package kg.study.lang.ast;

public class IfNode extends ConditionNode {

    public IfNode(BinaryOperation condition, Node body) {
        super(NodeType.IF, condition, body);
    }

}
