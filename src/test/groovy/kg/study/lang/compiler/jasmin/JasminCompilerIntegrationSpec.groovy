package kg.study.lang.compiler.jasmin

import jasmin.Main
import spock.lang.Specification
import static kg.study.lang.ast.NodeFactory.program

class JasminCompilerIntegrationSpec extends Specification {

    public static final File TEST_DIR = new File('target', 'test-jasmin-compiler')
    static final CLASS_NAME = 'HelloWorld'
    static final CLASS_FILE_NAME = CLASS_NAME + '.j'
    static final CLASS_FILE = new File(TEST_DIR, CLASS_FILE_NAME)
    def out
    def nativeOut

    void setup() {
        TEST_DIR.mkdirs()
        out = new ByteArrayOutputStream()
        nativeOut = System.out
        System.setOut(new PrintStream(out))
    }

    void cleanup() {
        TEST_DIR.deleteDir()
        System.setOut(nativeOut)
    }

    void "compiled class should work if empty node is given"() {
        given:
        compileNodeToFile(program(null, null))

        when:
        assembleFileToClassInPath(CLASS_FILE.absolutePath, TEST_DIR.path)
        loadClassFromTestDirectory(CLASS_NAME).main()

        then:
        assert out.toString() == '''Generated: target/test-jasmin-compiler/HelloWorld.class
'''
    }

    void "compiled class should work and print output if set int const and print nodes are given"() {
        given:
        compileNodeToFile(JasminCompilerSpec.SET_VARIABLE_TO_INT_AND_PRINT)

        when:
        assembleFileToClassInPath(CLASS_FILE.absolutePath, TEST_DIR.path)
        loadClassFromTestDirectory(CLASS_NAME).main()

        then:
        assert out.toString() == '''Generated: target/test-jasmin-compiler/HelloWorld.class
3
'''
    }

    void "compiled class should work and print output if set string const and print nodes are given"() {
        given:
        compileNodeToFile(JasminCompilerSpec.SET_VARIABLE_TO_STRING_AND_PRINT)

        when:
        assembleFileToClassInPath(CLASS_FILE.absolutePath, TEST_DIR.path)
        loadClassFromTestDirectory(CLASS_NAME).main()

        then:
        assert out.toString() == '''Generated: target/test-jasmin-compiler/HelloWorld.class
foo
'''
    }

    private static loadClassFromTestDirectory(String className) {
        URLClassLoader.newInstance([TEST_DIR.toURI().toURL()] as URL[]).loadClass(className)
    }

    private static void compileNodeToFile(node) {
        def compiler = new JasminCompiler()
        compiler.compile(node)
        CLASS_FILE.write(compiler.program)
    }

    private static void assembleFileToClassInPath(String filePath, String destPath) {
        def main = new Main()
        //noinspection GroovyAccessibility
        main.dest_path = destPath
        main.assemble(filePath)
    }
}
