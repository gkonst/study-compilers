package kg.study.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private final NodeType type;
    private Object value;
    private List<Node> children = new LinkedList<>();

    Node(NodeType type) {
        this.type = type;
    }

    Node(NodeType type, Object value) {
        this.type = type;
        this.value = value;
    }

    Node(NodeType type, Node... children) {
        this.type = type;
        this.children = Arrays.asList(children);
    }

    public Object getValue() {
        return value;
    }

    public NodeType getType() {
        return type;
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(Node node) {
        children.add(node);
    }

    @Override
    public String toString() {
        return "Node{" + type +
                ", " + value +
                ", " + children +
                '}';
    }

    public enum NodeType {
        VAR, CONST, ADD, SUB, LT, SET, IF, IFELSE, WHILE, DO, EMPTY, SEQ, EXPR, PROGRAM, PRINT
    }
}
