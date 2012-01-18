package kg.study.vm;

import kg.study.lang.Node;
import kg.study.lang.Parser;
import kg.study.lang.compiler.SimpleCompiler;
import kg.study.lang.lexer.Lexer;
import org.testng.annotations.Test;

import java.util.List;

public class VirtualMachineTest {
    @Test
    public void testRun() throws Exception {
        String given = "a = 3;";
        Parser parser = new Parser(new Lexer(given));
        Node nodes = parser.parse();
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.compile(nodes);
        List result = compiler.getProgram();
        System.out.println(result);
        VirtualMachine vm = new VirtualMachine();
        vm.run(result.toArray());
    }
}
