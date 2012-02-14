package kg.study.lang.lexer;

public class Identifier implements Token {
    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
