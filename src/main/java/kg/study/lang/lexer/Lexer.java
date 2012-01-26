package kg.study.lang.lexer;

public class Lexer {

    private final String source;
    private int position = 0;

    public Lexer(String source) {
        this.source = source;
    }

    public Token next() {
        Token result = null;
        while (result == null) {
            if (position == source.length()) {
                result = Token.EOF;
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
                return new ValueToken(value);
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
                    result = new Identifier(word.charAt(0) - (int) 'a');
                } else {
                    throw new IllegalArgumentException("Unexpected word : " + word);
                }
            } else {
                throw new IllegalArgumentException(
                        "Unexpected character : " + currChar() + " at position : " + position);
            }
        }
        return result;
    }

    private char currChar() {
        return source.charAt(position);
    }
}
