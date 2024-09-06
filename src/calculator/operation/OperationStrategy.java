package calculator.operation;

import calculator.operation.advanced.AdvancedOperation;
import calculator.operation.basic.BasicOperation;

import java.util.HashMap;
import java.util.Map;

public class OperationStrategy {
    private OperationMode mode;
    private Map<String, Operation> basicOperations = new HashMap<>();
    private Map<String, Operation> advancedOperations = new HashMap<>();

    public OperationStrategy(){
        this.mode = OperationMode.BASIC;

        //기본 산술 연산
        basicOperations.put(BasicOperation.ADD.getSymbol(), BasicOperation.ADD);
        basicOperations.put(BasicOperation.SUB.getSymbol(), BasicOperation.SUB);
        basicOperations.put(BasicOperation.MUL.getSymbol(), BasicOperation.MUL);
        basicOperations.put(BasicOperation.DIV.getSymbol(), BasicOperation.DIV);

        //고급 산술 연산
        advancedOperations.put(AdvancedOperation.SQUARE_ROOT.getSymbol(), AdvancedOperation.SQUARE_ROOT);
        advancedOperations.put(AdvancedOperation.POWER.getSymbol(), AdvancedOperation.POWER);
        advancedOperations.put(AdvancedOperation.SQUARE.getSymbol(), AdvancedOperation.SQUARE);
        advancedOperations.put(AdvancedOperation.FACTORIAL.getSymbol(), AdvancedOperation.FACTORIAL);
    }

    public void setMode(OperationMode mode){
        this.mode = mode;
    }

    public Operation getOperation(String symbol){
        return mode.equals(OperationMode.BASIC)?
                basicOperations.get(symbol):advancedOperations.get(symbol);
    }

    //단항 연산자 판별
    public boolean isUnary(String operation){
        if(mode.equals(OperationMode.BASIC)) return basicOperations.get(operation).isUnary();
        else return advancedOperations.get(operation).isUnary();
    }

}
