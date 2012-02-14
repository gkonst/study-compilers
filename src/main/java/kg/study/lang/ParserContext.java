package kg.study.lang;

import kg.study.lang.lexer.Lexer;
import kg.study.lang.lexer.Token;

public class ParserContext {
    private final Lexer lexer;
    public Token currentToken;
    public Token lookAhead;

    public ParserContext(Lexer lexer) {
        this.lexer = lexer;
    }

    public Token nextToken() {
        if (lookAhead == null) {
            currentToken = lexer.next();
        } else {
            currentToken = lookAhead;
        }
        if (currentToken != Token.EOF) {
            lookAhead = lexer.next();
        } else {
            lookAhead = null;
        }
        return currentToken;
    }
}
