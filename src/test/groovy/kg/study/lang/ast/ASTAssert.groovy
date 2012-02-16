package kg.study.lang.ast;


import kg.study.lang.NodeType

final class ASTAssert {

    static void assertPrintNode(node, name) {
        assert node instanceof PrintNode
        assert node.type == NodeType.PRINT
        shouldBeVariable node.variable, name
    }

    static void assertEmptyNode(node) {
        assert node instanceof EmptyNode
        assert node.type == NodeType.EMPTY
    }

    static void assertExprNode(node, name, value) {
        assert node instanceof ExpressionNode
        node.type == NodeType.EXPRESSION
        assert node.child != null
        assert node.child instanceof AssignNode
        assert node.child.type == NodeType.ASSIGN
        shouldBeVariable node.child.variable, name
        shouldBeConstant node.child.value, value
    }

    static void assertSeqNode(node, size) {
        assert node instanceof SeqNode
        assert node.type == NodeType.SEQ
        assert node.children.size() == size
    }

    static shouldBeConstant(node, value) {
        assert node instanceof ConstNode
        assert node.type == NodeType.CONST
        assert node.value == value
        true
    }

    static shouldBeVariable(node, name) {
        assert node instanceof VariableNode
        assert node.type == NodeType.VARIABLE
        assert node.name == name
        true
    }

    static shouldBeSum(node, Closure assertions) {
        assert node instanceof SumNode
        assert node.type == NodeType.SUM
        assertions.delegate = node
        assertions.resolveStrategy = Closure.DELEGATE_FIRST
        assertions()
    }

    static shouldBeSub(node, Closure assertions) {
        assert node instanceof SubNode
        assert node.type == NodeType.SUB
        assertions.delegate = node
        assertions.resolveStrategy = Closure.DELEGATE_FIRST
        assertions()
    }

    static shouldBeLT(node, Closure assertions) {
        assert node instanceof LTNode
        assert node.type == NodeType.LT
        assertions.delegate = node
        assertions.resolveStrategy = Closure.DELEGATE_FIRST
        assertions()
    }
}
