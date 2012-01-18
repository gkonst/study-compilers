package kg.study.lang.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

import kg.study.lang.Node;
import kg.study.lang.Parser;
import kg.study.lang.lexer.Lexer;
import kg.study.vm.VMInstruction;
import org.testng.annotations.Test;

import java.util.List;

public class SimpleCompilerTest {

    @Test
    public void compileShouldNotFail() throws Exception {
        // given
        String given = "if (a < 0) a = 5;";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        // when
        compiler.compile(nodes);
        // then
        List<?> result = compiler.getProgram();
        System.out.println(result);
        assertThat(result, hasSize(13));
        assertEquals(result.get(0), VMInstruction.IFETCH);
        assertEquals(result.get(1), 0);
        assertEquals(result.get(2), VMInstruction.IPUSH);
        assertEquals(result.get(3), 0);
        assertEquals(result.get(4), VMInstruction.ILT);
        assertEquals(result.get(5), VMInstruction.JZ);
        assertEquals(result.get(6), 12);
        assertEquals(result.get(7), VMInstruction.IPUSH);
        assertEquals(result.get(8), 5);
        assertEquals(result.get(9), VMInstruction.ISTORE);
        assertEquals(result.get(10), 0);
        assertEquals(result.get(11), VMInstruction.IPOP);
        assertEquals(result.get(12), VMInstruction.HALT);
    }
}
