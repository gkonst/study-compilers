package kg.study.lang.lexer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

public class LexerTest {

    public static final int A_CODE = 0;

    @Test
    public void nextShouldReturnValueExpression() {
        // given
        String given = "5988 4 20";
        Lexer lexer = new Lexer(given);
        // when
        Token result1 = lexer.next();
        Token result2 = lexer.next();
        Token result3 = lexer.next();
        Token result4 = lexer.next();
        // then
        assertTrue(result1 instanceof ValueToken);
        assertEquals(((ValueToken) result1).getValue(), 5988);
        assertTrue(result2 instanceof ValueToken);
        assertEquals(((ValueToken) result2).getValue(), 4);
        assertTrue(result3 instanceof ValueToken);
        assertEquals(((ValueToken) result3).getValue(), 20);
        assertEquals(result4, Token.EOF);
    }

    @Test
    public void nextShouldReturnExpression() throws Exception {
        // given
        final String given = " { a = 3; if (a < 0) a = 5; }";
        final Lexer lexer = new Lexer(given);
        final List<Token> result = new LinkedList<>();
        Token expression;
        // when
        while ((expression = lexer.next()) != Token.EOF) {
            result.add(expression);
        }
        // then
        assertThat(result, hasSize(16));
        assertEquals(result.get(0), Symbol.LBRA);
        assertIdentifierWithValue(result, 1, A_CODE, 3);
        assertEquals(result.get(5), Keyword.IF);
        assertEquals(result.get(6), Symbol.LPAR);
        assertIdentifier(result.get(7), A_CODE);
        assertEquals(result.get(8), Symbol.LESS);
        assertValueExpression(result.get(9), 0);
        assertEquals(result.get(10), Symbol.RPAR);
        assertIdentifierWithValue(result, 11, A_CODE, 5);
        assertEquals(result.get(15), Symbol.RBRA);
    }

    private static void assertIdentifierWithValue(List<Token> result, int startPosition, int code, int value) {
        assertIdentifier(result.get(startPosition), code);
        assertEquals(result.get(++startPosition), Symbol.EQ);
        assertValueExpression(result.get(++startPosition), value);
        assertEquals(result.get(++startPosition), Symbol.SEMICOLON);
    }

    private static void assertIdentifier(Token expression, int code) {
        assertThat(expression, is(instanceOf(Identifier.class)));
        assertEquals(((Identifier) expression).getName(), code);
    }

    private static void assertValueExpression(Token expression, int value) {
        assertThat(expression, is(instanceOf(ValueToken.class)));
        assertEquals(((ValueToken) expression).getValue(), value);
    }
}
