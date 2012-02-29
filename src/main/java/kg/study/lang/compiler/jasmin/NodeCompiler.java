package kg.study.lang.compiler.jasmin;

import kg.study.lang.ast.AssignNode;
import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.VariableNode;
import kg.study.lang.parser.VariablesTable;

import java.util.HashMap;
import java.util.Map;

public class NodeCompiler {

    // key - var name, value - index in locals
    private Map<String, Integer> locals = new HashMap<String, Integer>();
    private StringBuilder result = new StringBuilder();
    private int localsCount = 1; // 1 because we have one input parameter in main method
    private VariablesTable variables;

    public void compileNode(Node node) {
        switch (node.getType()) {
            case PROGRAM:
                variables = ((ProgramNode) node).getVariablesTable();
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
                String name = ((AssignNode) node).getVariable().getName();
                if (locals.containsKey(name)) {
                    localIndex = locals.get(name);
                } else {
                    localIndex = locals.size();
                    locals.put(name, localIndex);
                    localsCount++;
                }
                result.append(String.format("\t%cstore ", getTypeAwareOperationPrefix(name)));
                result.append(localIndex);
                result.append("\n");
                break;
            }
            case CONST:
                result.append("\tldc ");
                Object value = ((ConstNode) node).getValue();
                if (value instanceof String) {
                    value = "" + '"' + value + '"';
                }
                result.append(value);
                result.append("\n");
                break;
            case PRINT:
                result.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
                localsCount++;
                VariableNode variableNode = ((PrintNode) node).getVariable();
                compileNode(variableNode);
                result.append(String.format("\tinvokevirtual java/io/PrintStream/println(%s)V\n",
                        getJavaType(variableNode.getName())));
                break;
            case VARIABLE: {
                String name = ((VariableNode) node).getName();
                int localIndex = locals.get(name);
                result.append(String.format("\t%cload ", getTypeAwareOperationPrefix(name)));
                result.append(localIndex);
                result.append("\n");
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    private char getTypeAwareOperationPrefix(String variableName) {
        Class type = variables.getVariable(variableName).getType();
        if (Integer.class.equals(type)) {
            return 'i';
        } else {
            return 'a';
        }
    }

    private String getJavaType(String variableName) {
        Class type = variables.getVariable(variableName).getType();
        if (Integer.class.equals(type)) {
            return "I";
        } else {
            return "L" + type.getName().replace('.', '/') + ";";
        }
    }

    public int getLocalsCount() {
        return localsCount;
    }

    public StringBuilder getResult() {
        return result;
    }
}
