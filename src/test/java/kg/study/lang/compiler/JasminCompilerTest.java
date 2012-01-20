package kg.study.lang.compiler;

import kg.study.lang.Node;
import org.testng.annotations.Test;

public class JasminCompilerTest {
    @Test
    public void compileShouldNotFailIfEmptyNodeGiven() throws Exception {
        // given
        Node node = new Node(Node.NodeType.PROGRAM);
        JasminCompiler compiler = new JasminCompiler();
        // when
        compiler.compile(node);
        // then
        System.out.println(compiler.getProgram());
    }
}
