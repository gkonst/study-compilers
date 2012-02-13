package kg.study.lang.compiler;

import kg.study.lang.ast.AssignNode;
import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.VariableNode;

import java.util.HashMap;
import java.util.Map;

public class NodeCompiler {

    // key - var name, value - index in locals
    private Map<String, Integer> locals = new HashMap<String, Integer>();
    private StringBuilder result = new StringBuilder();
    private int localsCount = 1; // 1 because we have one input parameter in main method

    public void compileNode(Node node) {
        switch (node.getType()) {
            case PROGRAM:
                if (((ProgramNode) node).getSeqNode() != null) {
                    compileNode(((ProgramNode) node).getSeqNode());
                }
                break;
            case SEQ:
                for (Node child : ((SeqNode) node).getChildren()) {
                    compileNode(child);
                }
                break;
            case ASSIGN: {
                compileNode(((AssignNode) node).getValue());
                Integer localIndex;
                String varCode = ((AssignNode) node).getVariable().getName();
                if (locals.containsKey(varCode)) {
                    localIndex = locals.get(varCode);
                } else {
                    localIndex = locals.size();
                    locals.put(varCode, localIndex);
                    localsCount++;
                }
                result.append("\tistore ");
                result.append(localIndex);
                result.append("\n");
                break;
            }
            case CONST:
                result.append("\tbipush ");
                result.append(((ConstNode) node).getValue());
                result.append("\n");
                break;
            case PRINT:
                result.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
                localsCount++;
                compileNode(((PrintNode) node).getVariable());
                result.append("\tinvokevirtual java/io/PrintStream/println(I)V\n");
                break;
            case VARIABLE: {
                int localIndex = locals.get(((VariableNode) node).getName());
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
