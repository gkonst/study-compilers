package kg.study.lang.compiler;

import kg.study.lang.Node;
import kg.study.lang.Parser;
import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

import java.util.List;

public class SimpleCompilerTest {
    @Test
    public void testCompile() throws Exception {
        String given = "if (a < 0) a = 5;";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List result = compiler.getProgram();
        System.out.println(result);
    }
}
