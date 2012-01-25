package kg.study.vm;

public class Instruction {
    private final OperationCode opCode;
    private int operand;

    public Instruction(OperationCode opCode) {
        this.opCode = opCode;
    }

    public Instruction(OperationCode opCode, int operand) {
        this.opCode = opCode;
        this.operand = operand;
    }

    public OperationCode getOpCode() {
        return opCode;
    }

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return opCode.toString();
    }
}
