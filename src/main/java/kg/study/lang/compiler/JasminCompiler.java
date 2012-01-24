package kg.study.lang.compiler;

import kg.study.lang.ast.Node;

public class JasminCompiler implements Compiler {

    private StringBuilder program;


    @Override
    public void compile(Node node) {
        program = new StringBuilder();
        classDeclaration();
        defaultConstructor();
        startMainMethod();
        NodeCompiler nodeCompiler = new NodeCompiler();
        nodeCompiler.compileNode(node);
        limits(10/* TODO fix this */, nodeCompiler.getLocalsCount());
        program.append(nodeCompiler.getResult());
        returnVoid();
        endMethod();
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
