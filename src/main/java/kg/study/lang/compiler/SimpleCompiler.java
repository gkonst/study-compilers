package kg.study.lang.compiler;

import static kg.study.vm.VMInstruction.*;

import kg.study.lang.Node;

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
                gen(node.getValue());
                break;
            case CONST:
                gen(IPUSH);
                gen(node.getValue());
                break;
            case ADD:
                compile(node.getChildren().get(0));
                compile(node.getChildren().get(1));
                gen(IADD);
                break;
            case SUB:
                compile(node.getChildren().get(0));
                compile(node.getChildren().get(1));
                gen(ISUB);
                break;
            case LT:
                compile(node.getChildren().get(0));
                compile(node.getChildren().get(1));
                gen(ILT);
                break;
            case SET:
                compile(node.getChildren().get(1));
                gen(ISTORE);
                gen(node.getChildren().get(0).getValue());
                break;
            case IF:
                compile(node.getChildren().get(0));
                gen(JZ);
                int addr = pc;
                gen(0);
                compile(node.getChildren().get(1));
                program.set(addr, pc);
                break;
            case IFELSE:
                compile(node.getChildren().get(0));
                gen(JZ);
                int addr1 = pc;
                gen(0);
                compile(node.getChildren().get(1));
                gen(JMP);
                int addr2 = pc;
                gen(0);
                program.set(addr1, pc);
                compile(node.getChildren().get(2));
                program.set(addr2, pc);
                break;
            case WHILE:
                addr1 = pc;
                compile(node.getChildren().get(0));
                gen(JZ);
                addr2 = pc;
                gen(0);
                compile(node.getChildren().get(1));
                gen(JMP);
                gen(addr1);
                program.set(addr2, pc);
                break;
            case DO:
                addr = pc;
                compile(node.getChildren().get(0));
                compile(node.getChildren().get(1));
                gen(JNZ);
                gen(addr);
                break;
            case SEQ:
                compile(node.getChildren().get(0));
                compile(node.getChildren().get(1));
                break;
            case EXPR:
                compile(node.getChildren().get(0));
                /// i think this useless
                gen(IPOP);
                break;
            case PROGRAM:
                compile(node.getChildren().get(0));
                gen(HALT);
                break;
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    public List getProgram() {
        return program;
    }
}
