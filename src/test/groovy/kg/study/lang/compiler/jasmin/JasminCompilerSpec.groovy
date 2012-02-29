package kg.study.lang.compiler.jasmin;


import kg.study.lang.ast.Node
import kg.study.lang.parser.VariablesTable
import spock.lang.Specification
import static kg.study.lang.ast.NodeFactory.assign
import static kg.study.lang.ast.NodeFactory.constant
import static kg.study.lang.ast.NodeFactory.print
import static kg.study.lang.ast.NodeFactory.program
import static kg.study.lang.ast.NodeFactory.seq
import static kg.study.lang.ast.NodeFactory.var

class JasminCompilerSpec extends Specification {

    public static final Node SET_VARIABLE_TO_INT_AND_PRINT = program(
            seq(assign(var('a'), constant(3)), print(var('a'))), new VariablesTable().withVariable('a', Integer))

    public static final Node SET_VARIABLE_TO_STRING_AND_PRINT = program(
            seq(assign(var('a'), constant("foo")), print(var('a'))), new VariablesTable().withVariable('a', String))


    void "compile should work if empty node is given"() {
        given:
        def node = program(null, null);
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
        compiler.compile(SET_VARIABLE_TO_INT_AND_PRINT)

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

	ldc 3
	istore 0
	getstatic java/lang/System/out Ljava/io/PrintStream;
	iload 0
	invokevirtual java/io/PrintStream/println(I)V
   return
.end method
'''
    }

    void "compile should work if set of string const and print is given"() {
        given:
        def compiler = new JasminCompiler()

        when:
        compiler.compile(SET_VARIABLE_TO_STRING_AND_PRINT)

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

	ldc "foo"
	astore 0
	getstatic java/lang/System/out Ljava/io/PrintStream;
	aload 0
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   return
.end method
'''
    }
}
