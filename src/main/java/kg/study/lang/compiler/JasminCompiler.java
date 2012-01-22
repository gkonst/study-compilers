package kg.study.lang.compiler;

import kg.study.lang.Node;

import java.util.HashMap;
import java.util.Map;

public class JasminCompiler implements Compiler {

    private StringBuilder program;
    // key - var code, value - index in locals
    private Map<Integer, Integer> locals = new HashMap<>();

    @Override
    public void compile(Node node) {
        program = new StringBuilder();
        classDeclaration();
        defaultConstructor();
        startMainMethod();
        limits(countStack(node), countLocals(node) + 1);
        compileNode(node);
        returnVoid();
        endMethod();
    }

    private void compileNode(Node node) {
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
                    localIndex = locals.size() + 1;
                    locals.put((Integer) varCode, localIndex);
                }
                program.append("\tistore ");
                program.append(localIndex);
                program.append("\n");
                break;
            }
            case CONST:
                program.append("\tbipush ");
                program.append(node.getValue());
                program.append("\n");
                break;
            case PRINT:
                program.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");
                compileNode(node.getChildren().get(0));
                program.append("\tinvokevirtual java/io/PrintStream/println(I)V\n");
                break;
            case VAR: {
                int localIndex = locals.get(node.getValue());
                program.append("\tiload ");
                program.append(localIndex);
                program.append("\n");
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    private static int countLocals(Node node) {
        return node.count(new Node.NodeFilter() {
            @Override
            public boolean accept(Node node) {
                return node.getType() == Node.NodeType.SET;
            }
        });
    }

    private static int countStack(Node node) {
        return node.count(new Node.NodeFilter() {
            @Override
            public boolean accept(Node node) {
                return true;
            }
        }) - 1;
    }

    private void returnVoid() {
        program.append("   return\n");
    }

    private void limits(int stack, int locals) {
        program.append("   .limit stack ")
                .append(stack)
                .append("\n   .limit locals ")
                .append(locals)
                .append("\n\n");
    }

    private void endMethod() {
        program.append(".end method\n");
    }

    private void startMainMethod() {
        program.append(".method public static main([Ljava/lang/String;)V\n");
    }

    private void defaultConstructor() {
        program.append(".method public <init>()V\n")
                .append("   aload_0\n")
                .append("   invokenonvirtual java/lang/Object/<init>()V\n")
                .append("   return\n")
                .append(".end method\n\n");
    }

    private void classDeclaration() {
        program.append(".class public HelloWorld\n").append(".super java/lang/Object\n\n");
    }

    public String getProgram() {
        return program.toString();
    }
}
