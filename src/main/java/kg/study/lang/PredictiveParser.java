package kg.study.lang;

import static kg.study.lang.ast.NodeFactory.*;

import kg.study.lang.ast.BinaryOperation;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.VariableNode;
import kg.study.lang.lexer.Identifier;
import kg.study.lang.lexer.Keyword;
import kg.study.lang.lexer.Lexer;
import kg.study.lang.lexer.Symbol;
import kg.study.lang.lexer.Token;
import kg.study.lang.lexer.Value;


public class PredictiveParser {

    private final Lexer lexer;
    private Token currentToken;

    public PredictiveParser(Lexer lexer) {
        this.lexer = lexer;
    }

    private Node term() {
        Node node;
        if (currentToken instanceof Identifier) {
            node = var(((Identifier) currentToken).getName());
            nextToken();
        } else if (currentToken instanceof Value) {
            node = constant((Integer) ((Value) currentToken).getValue());
            nextToken();
        } else {
            node = paren();
        }
        return node;
    }

    private void nextToken() {
        currentToken = lexer.next();
    }

    private Node compare() {
        Node node = sum();
        if (currentToken == Symbol.LT) {
            nextToken();
            node = lt(node, sum());
        }
        return node;
    }

    private Node sum() {
        Node node = term();
        while (currentToken == Symbol.PLUS || currentToken == Symbol.MINUS) {
            if (currentToken == Symbol.PLUS) {
                nextToken();
                node = add(node, term());
            } else {
                nextToken();
                node = sub(node, term());
            }
        }
        return node;
    }

    private Node expression() {
        if (!(currentToken instanceof Identifier)) {
            return compare();
        }
        Node node = compare();
        if (node.getType() == NodeType.VARIABLE && currentToken == Symbol.ASSIGN) {
            nextToken();
            node = assign((VariableNode) node, expression());
        }
        return node;
    }

    private Node paren() {
        if (currentToken != Symbol.LPAR) {
            throw new IllegalArgumentException("'(' expected");
        }
        nextToken();
        Node node = expression();
        if (currentToken != Symbol.RPAR) {
            throw new IllegalArgumentException("')' expected");
        }
        nextToken();
        return node;
    }

    private Node statement() {
        Node node;
        if (currentToken == Keyword.IF) {
            nextToken();
            if (currentToken == Keyword.ELSE) {
                Node parenNode = paren();
                Node statementNode = statement();
                nextToken();
                node = ifElseNode((BinaryOperation) parenNode, statementNode, statement());
            } else {
                node = ifNode((BinaryOperation) paren(), statement());
            }
        } else if (currentToken == Keyword.WHILE) {
            nextToken();
            node = whileNode((BinaryOperation) paren(), statement());
        } else if (currentToken == Keyword.DO) {
            nextToken();
            Node statement = statement();
            if (currentToken != Keyword.WHILE) {
                throw new IllegalArgumentException("'while' expected");
            }
            nextToken();
            if (currentToken != Symbol.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            node = doNode((BinaryOperation) paren(), statement);
        } else if (currentToken == Symbol.SEMICOLON) {
            node = emptyNode();
            nextToken();
        } else if (currentToken == Symbol.LBRA) {
            node = seq();
            nextToken();
            while (currentToken != Symbol.RBRA) {
                ((SeqNode) node).addChild(statement());
            }
            nextToken();
        } else if (currentToken == Keyword.PRINT) {
            nextToken();
            node = print((VariableNode) paren());
        } else {
            node = expr(expression());
            if (currentToken != Symbol.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            nextToken();
        }

        return node;
    }

    public Node parse() {
        nextToken();
        Node node = program((SeqNode) statement());
        if (currentToken != Token.EOF) {
            throw new IllegalArgumentException("Invalid statement syntax");
        }
        return node;
    }
}
