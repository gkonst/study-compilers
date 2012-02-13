package kg.study.lang.lexer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ComplexSymbol implements Token {
    LE("<="),
    GE(">="),
    NOTEQUAL("!="),
    EQUAL("==");

    private final String text;
    public static final Map<String, ComplexSymbol> VALUES;
    public static final char[] START_SYMBOLS;

    private ComplexSymbol(String text) {
        this.text = text;
    }

    static {
        Map<String, ComplexSymbol> map = new HashMap<>();
        START_SYMBOLS = new char[values().length];
        int i = 0;
        for (ComplexSymbol symbol : values()) {
            map.put(symbol.text, symbol);
            START_SYMBOLS[i++] = symbol.text.charAt(0);
        }
        Arrays.sort(START_SYMBOLS);
        VALUES = Collections.unmodifiableMap(map);
    }
}
