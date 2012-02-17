package kg.study.lang.ast;


import static kg.study.lang.NodeType.*;

final class ASTMatchers {

    static void shouldBeSeq(node, Closure children) {
        invokeChildren(node.children.iterator(), children)
    }

    static shouldBeConst(node, value) {
        node.shouldBe ConstNode
        assert node.value == value
        true
    }

    static shouldBeVariable(node, name) {
        shouldBe(node, VariableNode)
        assert node.name == name
        true
    }

    static shouldBe(node, Class cls, Object... params) {
        shouldBe(node, cls)
        findShould(node).invoke(node, params)
        true
    }

    static void shouldBe(node, Class cls) {
        assert cls.isInstance(node)
        if (cls in NODE_TYPES) {
            assert node.type == NODE_TYPES[cls]
        } else {
            throw new IllegalArgumentException("$cls not found in NODE_TYPES -- may be you forgot to add them")
        }
    }

    static void shouldBe(node, Class cls, Closure children) {
        shouldBe(node, cls)
        def shouldMethod = findShould(node)
        if (shouldMethod) {
            shouldMethod.invoke(node, children)
        } else {
            invokeChildren(node, children)
        }
    }

    private static MetaMethod findShould(node) {
        node.metaClass.metaMethods.find {
            it.name == "shouldBe${node.type.name().toLowerCase().capitalize()}"
        }
    }

    private static invokeChildren(node, Closure children) {
        children.delegate = node
        children.resolveStrategy = Closure.DELEGATE_FIRST
        children.call()
    }

    private static final NODE_TYPES = [
            (ProgramNode): PROGRAM,
            (SeqNode): SEQ,
            (VariableNode): VARIABLE,
            (ConstNode): CONST,
            (SumNode): SUM,
            (SubNode): SUB,
            (LTNode): LT,
            (AssignNode): ASSIGN,
            (IfNode): IF,
            (ExpressionNode): EXPRESSION,
            (PrintNode): PRINT,
            (EmptyNode): EMPTY]
}
