package kg.study.lang.ast;

import kg.study.lang.NodeType;
import kg.study.lang.ParserContext;
import kg.study.lang.lexer.ComplexSymbol;
import kg.study.lang.lexer.Identifier;
import kg.study.lang.lexer.Symbol;
import kg.study.lang.lexer.Token;
import kg.study.lang.lexer.Value;

public class ExpressionNode extends Node {
    private Node child;

    public ExpressionNode(Node child) {
        super(NodeType.EXPRESSION);
        this.child = child;
    }

    public Node getChild() {
        return child;
    }

    public static Node parse(ParserContext ctx) {
        ctx.nextToken();
        Node left = parseLeft(ctx);
        Token lookAhead = ctx.lookAhead;
        if (lookAhead == Symbol.RPAR) {
            return left;
        }
        if (lookAhead instanceof Symbol) {
            return BinaryOperation.parse(left, ctx);
        } else if (lookAhead instanceof ComplexSymbol) {
            throw new UnsupportedOperationException("Not implemented yet for complex symbols");
        } else {
            // const or variable
            return left;
        }
    }

    private static Node parseLeft(ParserContext ctx) {
        Token left = ctx.currentToken;
        if (left instanceof Identifier) {
            return new VariableNode(((Identifier) left).getName());
        } else if (left instanceof Value) {
            return new ConstNode((Integer) ((Value) left).getValue());
        } else if (left == Symbol.LPAR) {
            Node expression = parse(ctx);
            if (ctx.nextToken() != Symbol.RPAR) {
                throw new IllegalArgumentException("AAAA");
            }
            return expression;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
