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
        Expression result1 = lexer.next();
        Expression result2 = lexer.next();
        Expression result3 = lexer.next();
        Expression result4 = lexer.next();
        // then
        assertTrue(result1 instanceof ValueExpression);
        assertEquals(((ValueExpression) result1).getValue(), 5988);
        assertTrue(result2 instanceof ValueExpression);
        assertEquals(((ValueExpression) result2).getValue(), 4);
        assertTrue(result3 instanceof ValueExpression);
        assertEquals(((ValueExpression) result3).getValue(), 20);
        assertEquals(result4, Expression.EOF);
    }

    @Test
    public void nextShouldReturnExpression() throws Exception {
        // given
        final String given = " { a = 3; if (a < 0) a = 5; }";
        final Lexer lexer = new Lexer(given);
        final List<Expression> result = new LinkedList<>();
        Expression expression;
        // when
        while ((expression = lexer.next()) != Expression.EOF) {
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

    private static void assertIdentifierWithValue(List<Expression> result, int startPosition, int code, int value) {
        assertIdentifier(result.get(startPosition), code);
        assertEquals(result.get(++startPosition), Symbol.EQ);
        assertValueExpression(result.get(++startPosition), value);
        assertEquals(result.get(++startPosition), Symbol.SEMICOLON);
    }

    private static void assertIdentifier(Expression expression, int code) {
        assertThat(expression, is(instanceOf(Identifier.class)));
        assertEquals(((Identifier) expression).getName(), code);
    }

    private static void assertValueExpression(Expression expression, int value) {
        assertThat(expression, is(instanceOf(ValueExpression.class)));
        assertEquals(((ValueExpression) expression).getValue(), value);
    }
}
