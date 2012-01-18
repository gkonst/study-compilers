package kg.study.lang.compiler;

import kg.study.lang.Lexer;
import kg.study.lang.Node;
import kg.study.lang.Parser;
import org.testng.annotations.Test;

import java.util.List;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
public class SimpleCompilerTest {
    @Test
    public void testCompile() throws Exception {
        String given = "if (a < 0) a = 5;";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List result = compiler.program;
        System.out.println(result);
    }
}
