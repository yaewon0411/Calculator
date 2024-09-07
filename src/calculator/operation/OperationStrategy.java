package calculator.operation;

import calculator.operation.advanced.AdvancedOperation;
import calculator.operation.basic.BasicOperation;

import java.util.HashMap;
import java.util.Map;

public class OperationStrategy {

    private Operation operation;

    public OperationStrategy() {
        operation = new BasicOperation();
    }

    public OperationStrategy(Operation operation){
        this.operation = operation;
    }
    public Operation getOperation(String symbol){
        return operation.getOperations().get(symbol);
    }
    public void setOperation(Operation operation){
        this.operation = operation;
    }

    //단항 연산자 판별
    public boolean isUnary(String symbol){
        return getOperation(symbol).isUnary();
    }

    public Number calculate(String symbol, Number a, Number b){
        return getOperation(symbol).calculate(a,b);
    }

}
