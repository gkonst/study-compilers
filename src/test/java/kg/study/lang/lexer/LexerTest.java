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

    @Test
    public void nextShouldReturnValueToken() {
        // given
        String given = "5988 4 20 3.1415 \"foo\"";
        Lexer lexer = Lexer.forString(given);
        // when
        Token result1 = lexer.next();
        Token result2 = lexer.next();
        Token result3 = lexer.next();
        Token result4 = lexer.next();
        Token result5 = lexer.next();
        Token result6 = lexer.next();
        // then
        assertValueEquals(result1, 5988);
        assertValueEquals(result2, 4);
        assertValueEquals(result3, 20);
        assertValueEquals(result4, 3.1415f);
        assertValueEquals(result5, "foo");
        assertEquals(result6, Token.EOF);
    }

    @Test(expectedExceptions = LexerException.class)
    public void nextShouldFailIfStringLiteralIsNotClosedAndEOF() {
        // given
        String given = "\"foo";
        Lexer lexer = Lexer.forString(given);
        // when
        Token result = lexer.next();
        // then
        //  exception should be raised
    }

    @Test(expectedExceptions = LexerException.class)
    public void nextShouldFailIfStringLiteralIsNotClosedAndNewLineExists() {
        // given
        String given = "\"foo\n\"bar\"";
        Lexer lexer = Lexer.forString(given);
        // when
        Token result = lexer.next();
        // then
        //  exception should be raised
    }

    @Test
    public void nextShouldWorkIfMultilineStringGiven() throws Exception {
        // given
        String given = "100\n5\n\"foo\"";
        Lexer lexer = Lexer.forString(given);
        // when
        Token result1 = lexer.next();
        Token result2 = lexer.next();
        Token result3 = lexer.next();
        Token result4 = lexer.next();
        // then
        assertValueEquals(result1, 100);
        assertValueEquals(result2, 5);
        assertValueEquals(result3, "foo");
        assertEquals(result4, Token.EOF);
    }

    @Test
    public void nextShouldWorkIfIdentifierIsInTheEnd() throws Exception {
        // given
        String given = "100 a";
        Lexer lexer = Lexer.forString(given);
        // when
        Token result1 = lexer.next();
        Token result2 = lexer.next();
        Token result3 = lexer.next();
        // then
        assertValueEquals(result1, 100);
        assertIdentifier(result2, "a");
        assertEquals(result3, Token.EOF);
    }

    private void assertValueEquals(Token value, Object equalsTo) {
        assertTrue(value instanceof Value);
        assertEquals(((Value) value).getValue(), equalsTo);
    }

    @Test
    public void nextShouldReturnTokens() throws Exception {
        // given
        final String given = " { a = 3; if (a < 0) a = 5; }";
        final Lexer lexer = Lexer.forString(given);
        final List<Token> result = new LinkedList<>();
        Token expression;
        // when
        while ((expression = lexer.next()) != Token.EOF) {
            result.add(expression);
        }
        // then
        assertThat(result, hasSize(16));
        assertEquals(result.get(0), Symbol.LBRA);
        assertIdentifierWithValue(result, 1, "a", 3);
        assertEquals(result.get(5), Keyword.IF);
        assertEquals(result.get(6), Symbol.LPAR);
        assertIdentifier(result.get(7), "a");
        assertEquals(result.get(8), Symbol.LESS);
        assertValueExpression(result.get(9), 0);
        assertEquals(result.get(10), Symbol.RPAR);
        assertIdentifierWithValue(result, 11, "a", 5);
        assertEquals(result.get(15), Symbol.RBRA);
    }

    private static void assertIdentifierWithValue(List<Token> result, int startPosition, String name, int value) {
        assertIdentifier(result.get(startPosition), name);
        assertEquals(result.get(++startPosition), Symbol.EQ);
        assertValueExpression(result.get(++startPosition), value);
        assertEquals(result.get(++startPosition), Symbol.SEMICOLON);
    }

    private static void assertIdentifier(Token expression, String name) {
        assertThat(expression, is(instanceOf(Identifier.class)));
        assertEquals(((Identifier) expression).getName(), name);
    }

    private static void assertValueExpression(Token expression, int value) {
        assertThat(expression, is(instanceOf(Value.class)));
        assertEquals(((Value) expression).getValue(), value);
    }
}
