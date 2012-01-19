package kg.study.lang;

import kg.study.lang.lexer.Expression;
import kg.study.lang.lexer.Identifier;
import kg.study.lang.lexer.Keyword;
import kg.study.lang.lexer.Lexer;
import kg.study.lang.lexer.Symbol;
import kg.study.lang.lexer.ValueExpression;

public class Parser {

    private final Lexer lexer;
    private Expression currentExpression;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    private Node term() {
        Node node;
        if (currentExpression instanceof Identifier) {
            node = new Node(Node.NodeType.VAR, ((Identifier) currentExpression).getName());
            nextExpression();
        } else if (currentExpression instanceof ValueExpression) {
            node = new Node(Node.NodeType.CONST, ((ValueExpression) currentExpression).getValue());
            nextExpression();
        } else {
            node = paren();
        }
        return node;
    }

    private void nextExpression() {
        currentExpression = lexer.next();
    }

    private Node compare() {
        Node node = sum();
        if (currentExpression == Symbol.LESS) {
            nextExpression();
            node = new Node(Node.NodeType.LT, node, sum());
        }
        return node;
    }

    private Node sum() {
        Node node = term();
        while (currentExpression == Symbol.PLUS || currentExpression == Symbol.MINUS) {
            if (currentExpression == Symbol.PLUS) {
                nextExpression();
                node = new Node(Node.NodeType.ADD, node, term());
            } else {
                nextExpression();
                node = new Node(Node.NodeType.SUB, node, term());
            }
        }
        return node;
    }

    private Node expression() {
        if (!(currentExpression instanceof Identifier)) {
            return compare();
        }
        Node node = compare();
        if (node.getType() == Node.NodeType.VAR && currentExpression == Symbol.EQ) {
            nextExpression();
            node = new Node(Node.NodeType.SET, node, expression());
        }
        return node;
    }

    private Node paren() {
        if (currentExpression != Symbol.LPAR) {
            throw new IllegalArgumentException("'(' expected");
        }
        nextExpression();
        Node node = expression();
        if (currentExpression != Symbol.RPAR) {
            throw new IllegalArgumentException("')' expected");
        }
        nextExpression();
        return node;
    }

    private Node statement() {
        Node node;
        if (currentExpression == Keyword.IF) {
            nextExpression();
            if (currentExpression == Keyword.ELSE) {
                Node parenNode = paren();
                Node statementNode = statement();
                nextExpression();
                node = new Node(Node.NodeType.IFELSE, parenNode, statementNode, statement());
            } else {
                node = new Node(Node.NodeType.IF, paren(), statement());
            }
        } else if (currentExpression == Keyword.WHILE) {
            nextExpression();
            node = new Node(Node.NodeType.WHILE, paren(), statement());
        } else if (currentExpression == Keyword.DO) {
            nextExpression();
            Node statement = statement();
            if (currentExpression != Keyword.WHILE) {
                throw new IllegalArgumentException("'while' expected");
            }
            nextExpression();
            if (currentExpression != Symbol.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            node = new Node(Node.NodeType.DO, statement, paren());
        } else if (currentExpression == Symbol.SEMICOLON) {
            node = new Node(Node.NodeType.EMPTY);
            nextExpression();
        } else if (currentExpression == Symbol.LBRA) {
            node = new Node(Node.NodeType.SEQ);
            nextExpression();
            while (currentExpression != Symbol.RBRA) {
                node.addChild(statement());
            }
            nextExpression();
        } else if (currentExpression == Keyword.PRINT) {
            nextExpression();
            node = new Node(Node.NodeType.PRINT, new Node[]{paren()});
        } else {
            node = new Node(Node.NodeType.EXPR, new Node[]{expression()});
            if (currentExpression != Symbol.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            nextExpression();
        }

        return node;
    }

    public Node parse() {
        nextExpression();
        Node node = new Node(Node.NodeType.PROGRAM, new Node[]{statement()});
        if (currentExpression != Expression.EOF) {
            throw new IllegalArgumentException("Invalid statement syntax");
        }
        return node;
    }
}
