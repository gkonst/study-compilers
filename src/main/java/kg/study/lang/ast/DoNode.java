package kg.study.lang.ast;

public class DoNode extends ConditionNode {
    protected DoNode(BinaryOperation condition, Node body) {
        super(NodeType.DO, condition, body);
    }
}
