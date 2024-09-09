package operation;

import operation.basic.BasicOperation;

public class OperationStrategy<T extends Number> {

    private Operation<T> operation;

    public OperationStrategy() {
        operation = new BasicOperation<T>(); //디폴트는 기본 산술 연산
    }

    public OperationStrategy(Operation<T> operation){
        this.operation = operation;
    }
    //연산 전략의 symbol(ex;ADD)연산 가져오기
    public Operation<T> getOperation(String symbol){
        return operation.getOperations().get(symbol);
    }
    //연사 전략 설정
    public void setOperation(Operation<T> operation){
        this.operation = operation;
    }

    //단항 연산자 판별
    public boolean isUnary(String symbol){
        return getOperation(symbol).isUnary();
    }

    //연산 수행
    public T calculate(String symbol, T a, T b){
        return getOperation(symbol).calculate(a,b);
    }

}
