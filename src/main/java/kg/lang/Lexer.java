package kg.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
enum Symbol implements Expression {
    LBRA('{'),
    RBRA('}'),
    EQ('='),
    SEMICOLON(';'),
    LPAR('('),
    RPAR(')'),
    PLUS('+'),
    MINUS('-'),
    LESS('<');

    char text;
    public static Map<Character, Symbol> mapOfValues;

    Symbol(char text) {
        this.text = text;
    }

    public static Map<Character, Symbol> getMapOfValues() {
        if (mapOfValues == null) {
            mapOfValues = new HashMap<>();
            for (Symbol symbol : values()) {
                mapOfValues.put(symbol.text, symbol);
            }
        }
        return mapOfValues;
    }
}

enum Keyword implements Expression {
    IF("if"),
    ELSE("else"),
    DO("do"),
    WHILE("while");

    String text;
    public static Map<String, Keyword> mapOfValues;

    Keyword(String text) {
        this.text = text;
    }

    public static Map<String, Keyword> getMapOfValues() {
        if (mapOfValues == null) {
            mapOfValues = new HashMap<>();
            for (Keyword symbol : values()) {
                mapOfValues.put(symbol.text, symbol);
            }
        }
        return mapOfValues;
    }
}

class ValueExpression implements Expression {
    Object value;

    ValueExpression(Object value) {
        this.value = value;
    }
}

class Identifier implements Expression {
    int name;

    Identifier(int name) {
        this.name = name;
    }
}

public class Lexer {


    String source;
    int position = 0;

    public Lexer(String source) {
        this.source = source;
    }

    public Expression next() {
        Expression result = null;
        while (result == null) {
            if (position == source.length()) {
                result = Expression.EOF;
            } else if (Character.isSpaceChar(currChar())) {
                position++;
            } else if (Symbol.getMapOfValues().containsKey(currChar())) {
                result = Symbol.getMapOfValues().get(currChar());
                position++;
            } else if (Character.isDigit(currChar())) {
                int value = 0;
                while (position < source.length() && Character.isDigit(currChar())) {
                    value = value * 10 + Character.digit(currChar(), 10);
                    position++;
                }
                return new ValueExpression(value);
            } else if (Character.isLetter(currChar())) {
                StringBuilder sb = new StringBuilder();
                while (position < source.length() && Character.isLetter(currChar())) {
                    sb.append(currChar());
                    position++;
                }
                String word = sb.toString();
                if (Keyword.getMapOfValues().containsKey(word)) {
                    result = Keyword.getMapOfValues().get(word);
                } else if (word.length() == 1) {
                    result = new Identifier(word.charAt(0) - (int)'a');
                } else {
                    throw new IllegalArgumentException("Unexpected word : " + word);
                }
            } else {
                throw new IllegalArgumentException("Unexpected character : " + currChar() + " at position : " + position);
            }
        }
        return result;
    }

    private char currChar() {
        return source.charAt(position);
    }
}
