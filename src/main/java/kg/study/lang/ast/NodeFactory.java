package kg.study.lang.ast;

public class NodeFactory {
    public static VariableNode var(String name) {
        return new VariableNode(name);
    }

    public static ConstNode constant(int value) {
        return new ConstNode(value);
    }

    public static AddNode add(Node left, Node right) {
        return new AddNode(left, right);
    }

    public static SubNode sub(Node left, Node right) {
        return new SubNode(left, right);
    }

    public static LTNode lt(Node left, Node right) {
        return new LTNode(left, right);
    }

    public static AssignNode assign(VariableNode var, Node constant) {
        return new AssignNode(var, constant);
    }

    public static ProgramNode program(SeqNode child) {
        return new ProgramNode(child);
    }

    public static WhileNode whileNode(BinaryOperation condition, Node body) {
        return new WhileNode(condition, body);
    }

    public static IfNode ifNode(BinaryOperation condition, Node body) {
        return new IfNode(condition, body);
    }

    public static IfElseNode ifElseNode(BinaryOperation condition, Node body, Node elseBody) {
        return new IfElseNode(condition, body, elseBody);
    }

    public static PrintNode print(VariableNode var) {
        return new PrintNode(var);
    }

    public static EmptyNode emptyNode() {
        return new EmptyNode();
    }

    public static SeqNode seq() {
        return new SeqNode();
    }

    public static SeqNode seq(Node... children) {
        return new SeqNode(children);
    }

    public static ExpressionNode expr(Node child) {
        return new ExpressionNode(child);
    }

    public static DoNode doNode(BinaryOperation condition, Node body) {
        return new DoNode(condition, body);
    }
}
