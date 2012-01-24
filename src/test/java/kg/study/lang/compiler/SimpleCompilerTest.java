package kg.study.lang.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

import kg.study.lang.Parser;
import kg.study.lang.ast.Node;
import kg.study.lang.lexer.Lexer;
import kg.study.vm.VMInstruction;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

public class SimpleCompilerTest {

    @Test
    public void compileShouldNotFail() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; print(a); }";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        // when
        compiler.compile(nodes);
        // then
        List<?> result = compiler.getProgram();
        System.out.println(result);
        assertThat(result, hasSize(22));
        Iterator it = result.iterator();
        assertNextValue(it, VMInstruction.IPUSH);
        assertNextValue(it, 3);
        assertNextValue(it, VMInstruction.ISTORE);
        assertNextValue(it, 0);
        assertNextValue(it, VMInstruction.IPOP);
        assertNextValue(it, VMInstruction.IFETCH);
        assertNextValue(it, 0);
        assertNextValue(it, VMInstruction.IPUSH);
        assertNextValue(it, 0);
        assertNextValue(it, VMInstruction.ILT);
        assertNextValue(it, VMInstruction.JZ);
        assertNextValue(it, 17);
        assertNextValue(it, VMInstruction.IPUSH);
        assertNextValue(it, 5);
        assertNextValue(it, VMInstruction.ISTORE);
        assertNextValue(it, 0);
        assertNextValue(it, VMInstruction.IPOP);
        assertNextValue(it, VMInstruction.IFETCH);
        assertNextValue(it, 0);
        assertNextValue(it, VMInstruction.PRINT);
        assertNextValue(it, VMInstruction.IPOP);
        assertNextValue(it, VMInstruction.HALT);
    }

    private static void assertNextValue(Iterator iterator, Object value) {
        assertEquals(iterator.next(), value);
    }
}
