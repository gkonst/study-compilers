package kg.study.lang.ast;

public class ConstNode extends Node {
    private final Object value;

    protected ConstNode(Object value) {
        super(NodeType.CONST);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getType() + "{" + value + '}';
    }
}
