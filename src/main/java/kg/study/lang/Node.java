package kg.study.lang;

import java.util.Arrays;
import java.util.List;

public class Node {
    public final NodeType type;
    public Object value;
    public List<Node> children;

    Node(NodeType type, Object value) {
        this.type = type;
        this.value = value;
    }

    Node(NodeType type, Node... children) {
        this.type = type;
        this.children = Arrays.asList(children);
    }

    public enum NodeType {
        VAR, CONST, ADD, SUB, LT, SET, IF, IFELSE, WHILE, DO, EMPTY, SEQ, EXPR, PROGRAM
    }
}
