package kg.study.lang.compiler;

import kg.study.lang.Node;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class JasminCompilerTest {

    public static final Node SET_AND_PRINT_NODE = new Node(Node.NodeType.PROGRAM, new Node[]{new Node(Node.NodeType.SEQ, new Node[]{
            new Node(Node.NodeType.SET,
                    new Node(Node.NodeType.VAR, 0),
                    new Node(Node.NodeType.CONST, 3)),
            new Node(Node.NodeType.PRINT,
                    new Node[]{new Node(Node.NodeType.VAR, 0)})})});

    @Test
    public void compileShouldNotFailIfEmptyNodeGiven() throws Exception {
        // given
        Node node = new Node(Node.NodeType.PROGRAM);
        JasminCompiler compiler = new JasminCompiler();
        // when
        compiler.compile(node);
        // then
        String result = compiler.getProgram();
        System.out.println(result);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(not(equalTo(""))));
    }

    @Test
    public void compileShouldNotFailIfSetAndPrintGive() throws Exception {
        // given
        JasminCompiler compiler = new JasminCompiler();
        // when
        compiler.compile(SET_AND_PRINT_NODE);
        // then
        String result = compiler.getProgram();
        System.out.println(result);
        assertThat(result, is(notNullValue()));
        assertThat(result, is(not(equalTo(""))));
    }
}
