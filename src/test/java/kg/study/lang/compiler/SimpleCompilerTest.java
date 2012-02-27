package kg.study.lang.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertEquals;

import kg.study.lang.ast.Node;
import kg.study.lang.lexer.Lexer;
import kg.study.lang.parser.PredictiveParser;
import kg.study.vm.Instruction;
import kg.study.vm.OperationCode;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

public class SimpleCompilerTest {

    @Test
    public void compileShouldNotFail() throws Exception {
        // given
        String given = " { a = 3; if (a < 0) a = 5; print(a); }";
        PredictiveParser parser = new PredictiveParser(Lexer.forString(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        // when
        compiler.compile(nodes);
        // then
        List<Instruction> result = compiler.getProgram();
        System.out.println(result);
        assertThat(result, hasSize(14));
        Iterator<Instruction> it = result.iterator();
        assertNextInstruction(it, OperationCode.IPUSH, 3);
        assertNextInstruction(it, OperationCode.ISTORE, 0);
        assertNextInstruction(it, OperationCode.IPOP);
        assertNextInstruction(it, OperationCode.IFETCH, 0);
        assertNextInstruction(it, OperationCode.IPUSH, 0);
        assertNextInstruction(it, OperationCode.ILT);
        assertNextInstruction(it, OperationCode.JZ, 10);
        assertNextInstruction(it, OperationCode.IPUSH, 5);
        assertNextInstruction(it, OperationCode.ISTORE, 0);
        assertNextInstruction(it, OperationCode.IPOP);
        assertNextInstruction(it, OperationCode.IFETCH, 0);
        assertNextInstruction(it, OperationCode.PRINT);
        assertNextInstruction(it, OperationCode.IPOP);
        assertNextInstruction(it, OperationCode.HALT);
    }

    private static void assertNextInstruction(Iterator<Instruction> iterator, OperationCode opCode, int operand) {
        Instruction instruction = iterator.next();
        assertEquals(instruction.getOpCode(), opCode);
        assertEquals(instruction.getOperand(), operand);
    }

    private static void assertNextInstruction(Iterator<Instruction> iterator, OperationCode opCode) {
        Instruction instruction = iterator.next();
        assertEquals(instruction.getOpCode(), opCode);
    }
}
