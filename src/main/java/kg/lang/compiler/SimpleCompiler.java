package kg.lang.compiler;

import kg.lang.Node;

import java.util.LinkedList;
import java.util.List;

import static kg.vm.VMInstruction.*;

/**
 * TODO add description
 *
 * @author Konstantin_Grigoriev
 */
public class SimpleCompiler implements Compiler {
    public List program = new LinkedList();
    int pc = 0;

    void gen(Object command) {
        program.add(command);
        pc++;
    }

    public void compile(Node node) {
        switch (node.type) {
            case VAR:
                gen(IFETCH);
                gen(node.value);
                break;
            case CONST:
                gen(IPUSH);
                gen(node.value);
                break;
            case ADD:
                compile(node.children.get(0));
                compile(node.children.get(1));
                gen(IADD);
                break;
            case SUB:
                compile(node.children.get(0));
                compile(node.children.get(1));
                gen(ISUB);
                break;
            case LT:
                compile(node.children.get(0));
                compile(node.children.get(1));
                gen(ILT);
                break;
            case SET:
                compile(node.children.get(1));
                gen(ISTORE);
                gen(node.children.get(0).value);
                break;
            case IF:
                compile(node.children.get(0));
                gen(JZ);
                int addr = pc;
                gen(0);
                compile(node.children.get(1));
                program.set(addr, pc);
                break;
            case IFELSE:
                compile(node.children.get(0));
                gen(JZ);
                int addr1 = pc;
                gen(0);
                compile(node.children.get(1));
                gen(JMP);
                int addr2 = pc;
                gen(0);
                program.set(addr1, pc);
                compile(node.children.get(2));
                program.set(addr2, pc);
                break;
            case WHILE:
                addr1 = pc;
                compile(node.children.get(0));
                gen(JZ);
                addr2 = pc;
                gen(0);
                compile(node.children.get(1));
                gen(JMP);
                gen(addr1);
                program.set(addr2, pc);
                break;
            case DO:
                addr = pc;
                compile(node.children.get(0));
                compile(node.children.get(1));
                gen(JNZ);
                gen(addr);
                break;
            case SEQ:
                compile(node.children.get(0));
                compile(node.children.get(1));
                break;
            case EXPR:
                compile(node.children.get(0));
                /// i think this useless
                gen(IPOP);
                break;
            case PROGRAM:
                compile(node.children.get(0));
                gen(HALT);
                break;
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

}
