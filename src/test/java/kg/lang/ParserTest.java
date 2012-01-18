package kg.lang;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
public class ParserTest {
    @Test
    public void parseShouldNotFail() throws Exception {
        System.out.println(0 + 'a');
        String given = "if (a < 0) a = 5;";
        Parser parser = new Parser(new Lexer(given));
        Node result = parser.parse();
        System.out.println(result);
    }
}
