package kg.study.lang.ast;

public class VariableNode extends Node {

    private final String name;

    protected VariableNode(String name) {
        super(NodeType.VARIABLE);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getType() + "{" + name + '}';
    }
}
