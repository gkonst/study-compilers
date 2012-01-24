package kg.study.lang.compiler;

import static kg.study.vm.VMInstruction.*;

import kg.study.lang.ast.AddNode;
import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.DoNode;
import kg.study.lang.ast.ExprNode;
import kg.study.lang.ast.IfElseNode;
import kg.study.lang.ast.IfNode;
import kg.study.lang.ast.LTNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.SetNode;
import kg.study.lang.ast.SubNode;
import kg.study.lang.ast.VarNode;
import kg.study.lang.ast.WhileNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SimpleCompiler implements Compiler {
    private final List program = new LinkedList();
    private int pc = 0;

    void gen(Object command) {
        program.add(command);
        pc++;
    }

    public void compile(Node node) {
        switch (node.getType()) {
            case VAR:
                gen(IFETCH);
                gen(((VarNode) node).getName());
                break;
            case CONST:
                gen(IPUSH);
                gen(((ConstNode) node).getValue());
                break;
            case ADD:
                compile(((AddNode) node).getLeft());
                compile(((AddNode) node).getRight());
                gen(IADD);
                break;
            case SUB:
                compile(((SubNode) node).getLeft());
                compile(((SubNode) node).getRight());
                gen(ISUB);
                break;
            case LT:
                compile(((LTNode) node).getLeft());
                compile(((LTNode) node).getRight());
                gen(ILT);
                break;
            case SET:
                compile(((SetNode) node).getValue());
                gen(ISTORE);
                gen(((SetNode) node).getVariable().getName());
                break;
            case IF:
                compile(((IfNode) node).getCondition());
                gen(JZ);
                int addr = pc;
                gen(0);
                compile(((IfNode) node).getBody());
                program.set(addr, pc);
                break;
            case IFELSE:
                compile(((IfElseNode) node).getCondition());
                gen(JZ);
                int addr1 = pc;
                gen(0);
                compile(((IfElseNode) node).getBody());
                gen(JMP);
                int addr2 = pc;
                gen(0);
                program.set(addr1, pc);
                compile(((IfElseNode) node).getElseBody());
                program.set(addr2, pc);
                break;
            case WHILE:
                addr1 = pc;
                compile(((WhileNode) node).getCondition());
                gen(JZ);
                addr2 = pc;
                gen(0);
                compile(((WhileNode) node).getBody());
                gen(JMP);
                gen(addr1);
                program.set(addr2, pc);
                break;
            case DO:
                addr = pc;
                compile(((DoNode) node).getBody());
                compile(((DoNode) node).getCondition());
                gen(JNZ);
                gen(addr);
                break;
            case SEQ:
                for (Node child : ((SeqNode) node).getChildren()) {
                    compile(child);
                }
                break;
            case EXPR:
                compile(((ExprNode) node).getChild());
                /// i think this useless
                gen(IPOP);
                break;
            case PROGRAM:
                compile(((ProgramNode) node).getSeqNode());
                gen(HALT);
                break;
            case EMPTY:
                // nothing to do
                break;
            case PRINT:
                compile(((PrintNode) node).getVariable());
                gen(PRINT);
                gen(IPOP);
                break;
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    public List getProgram() {
        return Collections.unmodifiableList(program);
    }
}
