package kg.study.lang.compiler;

import kg.study.lang.Node;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class JasminCompilerTest {
    @Test
    public void compileShouldNotFailIfEmptyNodeGiven() throws Exception {
        // given
        Node node = new Node(Node.NodeType.PROGRAM);
        JasminCompiler compiler = new JasminCompiler();
        // when
        compiler.compile(node);
        // then
        assertThat(compiler.getProgram(), is(notNullValue()));
        assertThat(compiler.getProgram(), is(not(equalTo(""))));
    }
}
