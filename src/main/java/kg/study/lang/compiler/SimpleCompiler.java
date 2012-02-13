package kg.study.lang.compiler;

import static kg.study.vm.OperationCode.*;

import kg.study.lang.ast.AddNode;
import kg.study.lang.ast.AssignNode;
import kg.study.lang.ast.ConstNode;
import kg.study.lang.ast.DoNode;
import kg.study.lang.ast.ExpressionNode;
import kg.study.lang.ast.IfElseNode;
import kg.study.lang.ast.IfNode;
import kg.study.lang.ast.LTNode;
import kg.study.lang.ast.Node;
import kg.study.lang.ast.PrintNode;
import kg.study.lang.ast.ProgramNode;
import kg.study.lang.ast.SeqNode;
import kg.study.lang.ast.SubNode;
import kg.study.lang.ast.VariableNode;
import kg.study.lang.ast.WhileNode;
import kg.study.vm.Instruction;
import kg.study.vm.OperationCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimpleCompiler implements Compiler {
    private final List<Instruction> program = new LinkedList<>();
    private int pc = 0;
    private Map<String, Integer> locals = new HashMap<String, Integer>();

    private void addInstruction(OperationCode opCode, int operand) {
        program.add(new Instruction(opCode, operand));
        pc++;
    }

    private void addInstruction(OperationCode opCode) {
        program.add(new Instruction(opCode));
        pc++;
    }

    private void changeOperand(int index, int operand) {
        program.get(index).setOperand(operand);
    }

    private int getLocalIndex(String name) {
        int result;
        if (locals.containsKey(name)) {
            result = locals.get(name);
        } else {
            result = locals.size();
            locals.put(name, result);
        }
        return result;
    }

    public void compile(Node node) {
        switch (node.getType()) {
            case VARIABLE:
                addInstruction(IFETCH, getLocalIndex(((VariableNode) node).getName()));
                break;
            case CONST:
                addInstruction(IPUSH, ((ConstNode) node).getValue());
                break;
            case ADD:
                compile(((AddNode) node).getLeft());
                compile(((AddNode) node).getRight());
                addInstruction(IADD);
                break;
            case SUB:
                compile(((SubNode) node).getLeft());
                compile(((SubNode) node).getRight());
                addInstruction(ISUB);
                break;
            case LT:
                compile(((LTNode) node).getLeft());
                compile(((LTNode) node).getRight());
                addInstruction(ILT);
                break;
            case ASSIGN:
                compile(((AssignNode) node).getValue());
                addInstruction(ISTORE, getLocalIndex(((AssignNode) node).getVariable().getName()));
                break;
            case IF: {
                compile(((IfNode) node).getCondition());
                int jzIndex = pc;
                addInstruction(JZ);
                compile(((IfNode) node).getBody());
                changeOperand(jzIndex, pc);
                break;
            }
            case IFELSE: {
                compile(((IfElseNode) node).getCondition());
                int jzIndex = pc;
                addInstruction(JZ);
                compile(((IfElseNode) node).getBody());
                int jmpIndex = pc;
                addInstruction(JMP);
                changeOperand(jzIndex, pc);
                compile(((IfElseNode) node).getElseBody());
                changeOperand(jmpIndex, pc);
                break;
            }
            case WHILE: {
                int whileStartIndex = pc;
                compile(((WhileNode) node).getCondition());
                int jzIndex = pc;
                addInstruction(JZ);
                compile(((WhileNode) node).getBody());
                addInstruction(JMP, whileStartIndex);
                changeOperand(jzIndex, pc);
                break;
            }
            case DO: {
                int doStartIndex = pc;
                compile(((DoNode) node).getBody());
                compile(((DoNode) node).getCondition());
                addInstruction(JNZ, doStartIndex);
                break;
            }
            case SEQ:
                for (Node child : ((SeqNode) node).getChildren()) {
                    compile(child);
                }
                break;
            case EXPRESSION:
                compile(((ExpressionNode) node).getChild());
                /// i think this useless
                addInstruction(IPOP);
                break;
            case PROGRAM:
                compile(((ProgramNode) node).getSeqNode());
                addInstruction(HALT);
                break;
            case EMPTY:
                // nothing to do
                break;
            case PRINT:
                compile(((PrintNode) node).getVariable());
                addInstruction(PRINT);
                addInstruction(IPOP);
                break;
            default:
                throw new UnsupportedOperationException("Unknown node : " + node);
        }
    }

    public List<Instruction> getProgram() {
        return Collections.unmodifiableList(program);
    }
}
