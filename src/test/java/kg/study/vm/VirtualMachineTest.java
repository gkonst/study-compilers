package kg.study.vm;

import static org.testng.Assert.assertEquals;

import kg.study.lang.ast.Node;
import kg.study.lang.compiler.SimpleCompiler;
import kg.study.lang.lexer.Lexer;
import kg.study.lang.parser.PredictiveParser;
import org.testng.annotations.Test;

import java.util.List;

public class VirtualMachineTest {

    @Test
    public void testRun() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; print(a); }";
        PredictiveParser parser = new PredictiveParser(Lexer.forString(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List<Instruction> program = compiler.getProgram();
        VirtualMachine vm = new VirtualMachine();
        // when
        vm.run(program.toArray(new Instruction[program.size()]));
        // then
        int aCode = VirtualMachine.nameToIndex('a');
        assertEquals(vm.getVar()[aCode], 3);
    }

    @Test
    public void testRun2() throws Exception {
        // given
        String given = " { a = 3; if (a < 4) a = 5; }";
        PredictiveParser parser = new PredictiveParser(Lexer.forString(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List<Instruction> program = compiler.getProgram();
        VirtualMachine vm = new VirtualMachine();
        // when
        vm.run(program.toArray(new Instruction[program.size()]));
        // then
        int aCode = VirtualMachine.nameToIndex('a');
        assertEquals(vm.getVar()[aCode], 5);
    }
}
