package kg.study.lang.compiler.jasmin;

import static kg.study.lang.ast.NodeFactory.assign;
import static kg.study.lang.ast.NodeFactory.constant;
import static kg.study.lang.ast.NodeFactory.print;
import static kg.study.lang.ast.NodeFactory.program;
import static kg.study.lang.ast.NodeFactory.seq;
import static kg.study.lang.ast.NodeFactory.var;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import kg.study.lang.ast.Node;
import org.testng.annotations.Test;

public class JasminCompilerTest {

    public static final Node SET_AND_PRINT_NODE = program(
            seq(assign(var("a"), constant(3)), print(var("a"))));

    @Test
    public void compileShouldNotFailIfEmptyNodeGiven() throws Exception {
        // given
        Node node = program(null);
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
