package kg.study.lang.lexer;

import java.util.HashMap;
import java.util.Map;

public enum Keyword implements Expression {
    IF("if"),
    ELSE("else"),
    DO("do"),
    WHILE("while");

    private final String text;
    private static Map<String, Keyword> mapOfValues;

    private Keyword(String text) {
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
