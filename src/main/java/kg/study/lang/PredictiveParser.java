package kg.study.lang;

import static kg.study.lang.ast.NodeFactory.*;

import kg.study.lang.ast.BinaryOperation;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.VarNode;
import kg.study.lang.lexer.Identifier;
import kg.study.lang.lexer.Keyword;
import kg.study.lang.lexer.Lexer;
import kg.study.lang.lexer.Symbol;
import kg.study.lang.lexer.Token;
import kg.study.lang.lexer.ValueToken;


public class PredictiveParser {

    private final Lexer lexer;
    private Token currentExpression;

    public PredictiveParser(Lexer lexer) {
        this.lexer = lexer;
    }

    private Node term() {
        Node node;
        if (currentExpression instanceof Identifier) {
            node = var(((Identifier) currentExpression).getName());
            nextExpression();
        } else if (currentExpression instanceof ValueToken) {
            node = constant((Integer) ((ValueToken) currentExpression).getValue());
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
            node = lt(node, sum());
        }
        return node;
    }

    private Node sum() {
        Node node = term();
        while (currentExpression == Symbol.PLUS || currentExpression == Symbol.MINUS) {
            if (currentExpression == Symbol.PLUS) {
                nextExpression();
                node = add(node, term());
            } else {
                nextExpression();
                node = sub(node, term());
            }
        }
        return node;
    }

    private Node expression() {
        if (!(currentExpression instanceof Identifier)) {
            return compare();
        }
        Node node = compare();
        if (node.getType() == NodeType.VAR && currentExpression == Symbol.EQ) {
            nextExpression();
            node = set((VarNode) node, expression());
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
                node = ifElseNode((BinaryOperation) parenNode, statementNode, statement());
            } else {
                node = ifNode((BinaryOperation) paren(), statement());
            }
        } else if (currentExpression == Keyword.WHILE) {
            nextExpression();
            node = whileNode((BinaryOperation) paren(), statement());
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
            node = doNode((BinaryOperation) paren(), statement);
        } else if (currentExpression == Symbol.SEMICOLON) {
            node = emptyNode();
            nextExpression();
        } else if (currentExpression == Symbol.LBRA) {
            node = seq();
            nextExpression();
            while (currentExpression != Symbol.RBRA) {
                ((SeqNode) node).addChild(statement());
            }
            nextExpression();
        } else if (currentExpression == Keyword.PRINT) {
            nextExpression();
            node = print((VarNode) paren());
        } else {
            node = expr(expression());
            if (currentExpression != Symbol.SEMICOLON) {
                throw new IllegalArgumentException("';' expected");
            }
            nextExpression();
        }

        return node;
    }

    public Node parse() {
        nextExpression();
        Node node = program((SeqNode) statement());
        if (currentExpression != Token.EOF) {
            throw new IllegalArgumentException("Invalid statement syntax");
        }
        return node;
    }
}
