package calculator.operation;

import calculator.operation.Operation;
import calculator.operation.OperationStrategy;

import java.util.ArrayList;
import java.util.List;

public class CalculatorState {
    private String firstNum;
    private String operation;
    private String secondNum;
    private List<Number> calculatedResults = new ArrayList<>();

    public List<Number> getCalculatedResults() {
        return calculatedResults;
    }

    public void setFirstNum(String firstNum) {
        this.firstNum = firstNum;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setSecondNum(String secondNum) {
        this.secondNum = secondNum;
    }


    public String getFirstNum() {
        return firstNum;
    }

    public String getOperation() {
        return operation;
    }

    public String getSecondNum() {
        return secondNum;
    }

    public void reset() {
        firstNum = null;
        operation = null;
        secondNum = null;
    }
    public void removeHistory(){
        this.calculatedResults = null;
    }
    public boolean isReadyToCalculate(OperationStrategy operationStrategy){
        return firstNum != null
                && operation != null && (operationStrategy.isUnary(operation) ||secondNum != null);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("[입력 내역 : ");
        if (firstNum != null) sb.append(firstNum).append(" ");
        if (operation != null) sb.append(operation).append(" ");
        if (secondNum != null) sb.append(secondNum);
        sb.append("]");
        return sb.toString();
    }
}
