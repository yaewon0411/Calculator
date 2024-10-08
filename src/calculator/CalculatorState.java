package calculator;

import operation.OperationStrategy;

import java.util.ArrayList;
import java.util.List;

public class CalculatorState{
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
    //모든 연산 결과 삭제
    public void clearAllHistory(){
        this.calculatedResults.clear();
    }

    // 가장 먼저 저장된 연산 결과 삭제
    public boolean removeOldestResult() {
        if (!calculatedResults.isEmpty()) {
            calculatedResults.remove(0);
            return true;
        }
        else return false;
    }

    //입력으로 받은 값보다 큰 결과 값들 출력
    public List<Number> getResultsGreaterThan(double threshold){
        return calculatedResults.stream()
                .filter(r -> r.doubleValue() > threshold)
                .toList();
    }

    //계산에 필요한 피연산자와 연산자를 입력받았는지 검증
    public boolean isReadyToCalculate(OperationStrategy operationStrategy){
        return firstNum != null
                && operation != null && (operationStrategy.isUnary(operation) ||secondNum != null);
    }
    //현재 입력 내역 출력
    public String toString() {
        StringBuilder sb = new StringBuilder("[입력 내역 : ");
        if (firstNum != null) sb.append(firstNum).append(" ");
        if (operation != null) sb.append(operation).append(" ");
        if (secondNum != null) sb.append(secondNum);
        sb.append("]");
        return sb.toString();
    }
}
