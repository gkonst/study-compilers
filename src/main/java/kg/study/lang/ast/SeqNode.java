package kg.study.lang.ast;

import kg.study.lang.NodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class SeqNode extends Node {
    private final Collection<Node> children = new LinkedList<>();

    public SeqNode(Node... children) {
        super(NodeType.SEQ);
        this.children.addAll(Arrays.asList(children));
    }

    public Collection<Node> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
