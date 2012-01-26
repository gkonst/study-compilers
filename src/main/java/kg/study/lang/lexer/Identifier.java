package kg.study.lang.lexer;

public class Identifier implements Token {
    private final int name;

    Identifier(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }
}
