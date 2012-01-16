package kg.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
public class LexerTest {
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
        Assert.assertEquals(((ValueExpression) result1).value, 5988l);
        assertTrue(result2 instanceof ValueExpression);
        Assert.assertEquals(((ValueExpression) result2).value, 4l);
        assertTrue(result3 instanceof ValueExpression);
        Assert.assertEquals(((ValueExpression) result3).value, 20l);
        Assert.assertEquals(result4, Expression.EOF);
    }

    @Test
    public void nextShouldReturnExpression() throws Exception {
        String given = " { a = 3; if (a < 0) a = 5; }";
        Lexer lexer = new Lexer(given);
        List<Expression> result = new LinkedList<>();
        Expression expression;
        while((expression = lexer.next()) != Expression.EOF){
            result.add(expression);
        }
        System.out.println(result);
    }
}
