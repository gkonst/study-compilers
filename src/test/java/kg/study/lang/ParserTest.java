package kg.study.lang;

import org.testng.annotations.Test;

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
