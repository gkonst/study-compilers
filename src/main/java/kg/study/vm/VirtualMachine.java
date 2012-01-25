package kg.study.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Stack;

public class VirtualMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualMachine.class);

    private int[] var;
    private Stack<Integer> stack;
    private int pc;
    private boolean isRunning = false;

    public void run(Instruction[] instructions) {
        var = new int[26];
        Arrays.fill(var, 0);
        stack = new Stack<>();
        pc = 0;
        isRunning = true;
        while (isRunning) {
            Instruction instruction = instructions[pc];
            run(instruction.getOpCode(), instruction.getOperand());
        }
        LOGGER.info("Execution finished.");
        for (int i = 0; i < 26; i++) {
            if (var[i] != 0) {
                LOGGER.debug("{} = {}", indexToName(i), var[i]);
            }
        }
    }

    private void run(OperationCode opCode, int operand) {
        switch (opCode) {
            case IFETCH: {
                stack.add(var[operand]);
                pc++;
                break;
            }
            case ISTORE: {
                var[operand] = stack.peek();
                pc++;
                break;
            }
            case IPUSH: {
                stack.add(operand);
                pc++;
                break;
            }
            case IPOP: {
                stack.pop();
                pc++;
                break;
            }
            case IADD: {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) + stack.get(stack.size() - 1));
                stack.pop();
                pc++;
                break;
            }
            case ISUB: {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) - stack.get(stack.size() - 1));
                stack.pop();
                pc++;
                break;
            }
            case ILT: {
                if (stack.get(stack.size() - 2) < stack.get(stack.size() - 1)) {
                    stack.set(stack.size() - 2, 1);
                } else {
                    stack.set(stack.size() - 2, 0);
                }
                stack.pop();
                pc++;
                break;
            }
            case JZ: {
                if (stack.pop() == 0) {
                    pc = operand;
                } else {
                    pc++;
                }
                break;
            }
            case JNZ: {
                if (stack.pop() != 0) {
                    pc = operand;
                } else {
                    pc++;
                }
                break;
            }
            case JMP: {
                pc = operand;
                break;
            }
            case PRINT: {
                System.out.println(stack.peek());
                pc++;
                break;
            }
            case HALT:
                isRunning = false;
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation code : " + opCode);
        }
        LOGGER.debug("{} : {}", opCode, stack);
    }

    public int[] getVar() {
        return var;
    }

    public static char indexToName(int index) {
        return (char) (index + (int) 'a');
    }

    public static int nameToIndex(char name) {
        return (int) name - (int) 'a';
    }
}
