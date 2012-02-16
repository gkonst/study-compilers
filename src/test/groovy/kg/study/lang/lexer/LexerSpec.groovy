package kg.study.lang.lexer

import spock.lang.Specification

@SuppressWarnings('MethodNam e')
class LexerSpec extends Specification {

    void "next should return Value token"() {
        expect:
        assertValue lexer.next(), 5988
        assertValue lexer.next(), 4
        assertValue lexer.next(), 20
        assertValue lexer.next(), 3.1415f
        assertValue lexer.next(), 'foo'

        where:
        input = '5988 4 20 3.1415 "foo"'
        lexer = Lexer.forString(input)
    }

    void "next should fail if string literal isn't closed and EOF is reached"() {
        given:
        def input = '"foo'
        def lexer = Lexer.forString(input)

        when:
        lexer.next()

        then:
        thrown LexerException
    }

    void "next should fail if string literal isn't closed and EOL is reached"() {
        given:
        def input = '"foo\n"bar"'
        def lexer = Lexer.forString(input)

        when:
        lexer.next()

        then:
        thrown LexerException
    }

    void "next should work if multiline string is given"() {
        expect:
        assertValue lexer.next(), 100
        assertValue lexer.next(), 5
        assertValue lexer.next(), 'foo'
        lexer.next() == Token.EOF

        where:
        input = '100\n5\n"foo"'
        lexer = Lexer.forString(input)
    }

    void "next should fail if identifier starts from digits"() {
        given:
        def given = '100a'
        def lexer = Lexer.forString(given)

        when:
        lexer.next()

        then:
        thrown LexerException
    }

    void "next should work if identifier contains digits"() {
        expect:
        assertIdentifier lexer.next(), 'a1a'
        assert lexer.next() == Token.EOF

        where:
        given = 'a1a'
        lexer = Lexer.forString(given)
    }

    void "next should work if identifier is in the end"() {
        expect:
        assertValue lexer.next(), 100
        assertIdentifier lexer.next(), 'a'
        assert lexer.next() == Token.EOF

        where:
        given = '100 a'
        lexer = Lexer.forString(given)
    }

    void "next should work with various input lexems"() {
        expect:
        assert lexer.next() == Symbol.LBRA
        assertIdentifierWithValue lexer, 'a', 3
        assert lexer.next() == Symbol.SEMICOLON
        assert lexer.next() == Keyword.IF
        assert lexer.next() == Symbol.LPAR
        assertIdentifier lexer.next(), 'a'
        assert lexer.next() == Symbol.LT
        assertValue lexer.next(), 0
        assert lexer.next() == Symbol.RPAR
        assertIdentifierWithValue lexer, 'a', 5
        assert lexer.next() == Symbol.SEMICOLON
        assert lexer.next() == Symbol.RBRA
        assert lexer.next() == Token.EOF


        where:
        given = ' { a = 3; if (a < 0) a = 5; }'
        lexer = Lexer.forString(given)
    }

    void "next should work if complex symbols exist in input"() {
        expect:
        assert lexer.next() == Keyword.DEF
        assertIdentifier lexer.next(), 'foo'
        assert lexer.next() == Symbol.LPAR
        assert lexer.next() == Symbol.RPAR
        assert lexer.next() == Symbol.LBRA
        assert lexer.next() == Keyword.VAL
        assertIdentifierWithValue lexer, 'a', 4
        assert lexer.next() == Keyword.VAR
        assertIdentifier lexer.next(), 'b'
        assert lexer.next() == Keyword.IF
        assert lexer.next() == Symbol.LPAR
        assertIdentifier lexer.next(), 'a'
        assert lexer.next() == ComplexSymbol.EQUAL
        assertValue lexer.next(), 3
        assert lexer.next() == Symbol.RPAR
        assert lexer.next() == Symbol.LBRA
        assertIdentifierWithValue lexer, 'b', 0
        assert lexer.next() == Symbol.RBRA
        assert lexer.next() == Keyword.ELSE
        assert lexer.next() == Symbol.LBRA
        assertIdentifierWithValue lexer, 'b', 3
        assert lexer.next() == Symbol.RBRA
        assert lexer.next() == Symbol.RBRA

        where:
        given = '''
def foo() {
    val a = 4
    var b
    if(a==3) {
        b = 0
    } else {
        b = 3
    }
}
'''
        lexer = Lexer.forString(given)
    }

    private static void assertValue(value, equalsTo) {
        assert value instanceof Value
        assert value.value == equalsTo
    }

    private static void assertIdentifier(expression, name) {
        assert expression instanceof Identifier
        assert expression.name == name
    }

    private static void assertIdentifierWithValue(lexer, name, value) {
        assertIdentifier lexer.next(), name
        assert lexer.next() == Symbol.ASSIGN
        assertValue lexer.next(), value
    }
}
