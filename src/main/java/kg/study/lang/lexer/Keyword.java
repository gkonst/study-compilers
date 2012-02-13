package kg.study.lang.lexer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Keyword implements Token {
    IF("if"),
    ELSE("else"),
    DO("do"),
    WHILE("while"),
    DEF("def"),
    VAL("val"),
    VAR("var"),
    PRINT("print");

    private final String text;
    public static final Map<String, Keyword> VALUES;

    private Keyword(String text) {
        this.text = text;
    }

    static {
        Map<String, Keyword> map = new HashMap<>();
        for (Keyword symbol : values()) {
            map.put(symbol.text, symbol);
        }
        VALUES = Collections.unmodifiableMap(map);
    }
}
