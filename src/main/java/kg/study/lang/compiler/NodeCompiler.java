package kg.study.lang.compiler;

import kg.study.lang.Node;

import java.util.HashMap;
import java.util.Map;

public class NodeCompiler {

    // key - var code, value - index in locals
    private Map<Integer, Integer> locals = new HashMap<>();
    private StringBuilder result = new StringBuilder();
    private int localsCount = 1; // 1 because we have one input parameter in main method

    public void compileNode(Node node) {
        switch (node.getType()) {
            case PROGRAM:
                if (!node.getChildren().isEmpty()) {
                    compileNode(node.getChildren().get(0));
                }
                break;
            case SEQ:
                for (Node child : node.getChildren()) {
                    compileNode(child);
                }
                break;
            case SET: {
                compileNode(node.getChildren().get(1));
                int localIndex;
                Object varCode = node.getChildren().get(0).getValue();
                if (locals.containsKey(varCode)) {
                    localIndex = locals.get(varCode);
                } else {
                    localIndex = locals.size();
                    locals.put((Integer) varCode, localIndex);
                    localsCount++;
                }
                result.append("\tistore ");
                result.append(localIndex);
                result.append("\n");
                break;
            }
            case CONST:
                result.append("\tbipush ");
                result.append(node.getValue());
                result.append("\n");
                break;
            case PRINT:
                result.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
                localsCount++;
                compileNode(node.getChildren().get(0));
                result.append("\tinvokevirtual java/io/PrintStream/println(I)V\n");
                break;
            case VAR: {
                int localIndex = locals.get(node.getValue());
                result.append("\tiload ");
                result.append(localIndex);
                result.append("\n");
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    public int getLocalsCount() {
        return localsCount;
    }

    public StringBuilder getResult() {
        return result;
    }
}
