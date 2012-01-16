package kg.lang;

import kg.lang.Expression;
import kg.lang.Identifier;
import kg.lang.Keyword;
import kg.lang.Lexer;
import kg.lang.Node;
import kg.lang.Symbol;
import kg.lang.ValueExpression;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
public class Parser {

    Lexer lexer;
    Expression currentExpression;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    Node term() {
        Node node;
        if (currentExpression instanceof Identifier) {
            node = new Node(Node.NodeType.VAR, ((Identifier) currentExpression).name);
            nextExpression();
        } else if (currentExpression instanceof ValueExpression) {
            node = new Node(Node.NodeType.CONST, ((ValueExpression) currentExpression).value);
            nextExpression();
        } else {
            node = paren();
        }
        return node;
    }

    private void nextExpression() {
        currentExpression = lexer.next();
    }

    Node compare() {
        Node node = sum();
        if (currentExpression == Symbol.LESS) {
            nextExpression();
            node = new Node(Node.NodeType.LT, node, sum());
        }
        return node;
    }

    Node sum() {
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

    Node expression() {
        if (!(currentExpression instanceof Identifier)) {
            return compare();
        }
        Node node = compare();
        if (node.type == Node.NodeType.VAR && currentExpression == Symbol.EQ) {
            nextExpression();
            node = new Node(Node.NodeType.SET, node, expression());
        }
        return node;
    }

    Node paren() {
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

    Node statement() {
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
            node = new Node(Node.NodeType.EMPTY);
            nextExpression();
            while (currentExpression != Symbol.RBRA) {
                node = new Node(Node.NodeType.SEQ, node, statement());
            }
            nextExpression();
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
