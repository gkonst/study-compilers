package kg.study.lang.compiler.jasmin;


import kg.study.lang.ast.Node
import spock.lang.Specification
import static kg.study.lang.ast.NodeFactory.assign
import static kg.study.lang.ast.NodeFactory.constant
import static kg.study.lang.ast.NodeFactory.print
import static kg.study.lang.ast.NodeFactory.program
import static kg.study.lang.ast.NodeFactory.seq
import static kg.study.lang.ast.NodeFactory.var

class JasminCompilerSpec extends Specification {

    public static final Node SET_AND_PRINT_NODE = program(
            seq(assign(var('a'), constant(3)), print(var('a'))))


    void "compile should work if empty node is given"() {
        given:
        def node = program(null);
        def compiler = new JasminCompiler()

        when:
        compiler.compile(node)

        then:
        assert compiler.program == '''.class public HelloWorld
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 1

   return
.end method
'''
    }

    void "compile should work if set of integer const and print is given"() {
        given:
        def compiler = new JasminCompiler()

        when:
        compiler.compile(SET_AND_PRINT_NODE)

        then:
        assert compiler.program == '''.class public HelloWorld
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
   .limit stack 10
   .limit locals 3

	bipush 3
	istore 0
	getstatic java/lang/System/out Ljava/io/PrintStream;
	iload 0
	invokevirtual java/io/PrintStream/println(I)V
   return
.end method
'''
    }
}
