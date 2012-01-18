package kg.study.vm;

import static org.testng.Assert.assertEquals;

import kg.study.lang.Node;
import kg.study.lang.Parser;
import kg.study.lang.compiler.SimpleCompiler;
import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

import java.util.List;

public class VirtualMachineTest {
    @Test
    public void testRun() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; }";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List program = compiler.getProgram();
        VirtualMachine vm = new VirtualMachine();
        // when
        vm.run(program.toArray());
        // then
        int aCode = VirtualMachine.nameToIndex('a');
        assertEquals(vm.getVar()[aCode], 3);
    }

    @Test
    public void testRun2() throws Exception {
        // given
        String given = " { a = 3; if (a < 4) a = 5; }";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List program = compiler.getProgram();
        VirtualMachine vm = new VirtualMachine();
        // when
        vm.run(program.toArray());
        // then
        int aCode = VirtualMachine.nameToIndex('a');
        assertEquals(vm.getVar()[aCode], 5);
    }
}
