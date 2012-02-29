package kg.study.lang.parser;

import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.Node;

public class TypesAnalyzer {


    public static Class inferType(Node node) {
        switch (node.getType()) {
            case CONST:
                return ((ConstNode) node).getValue().getClass();
            default:
                throw new UnsupportedOperationException("Can't infer type for node : " + node);
        }
    }
}
