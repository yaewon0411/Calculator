package calculator.operation.basic;

import calculator.operation.Operation;
import calculator.operation.OperationMode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiFunction;

//기본 산술 연산자: 덧셈, 뺄셈, 곱셈, 나눗셈
public class BasicOperation implements Operation {

    private String symbol;
    private BiFunction<Number, Number, Number> operation;
    private boolean isUnary; //단항 연산자이면 true, 아니면 false


    private final Map<String, Operation> operations = new HashMap<>();

    public BasicOperation(){
        operations.put(ADD.getSymbol(), ADD);
        operations.put(SUB.getSymbol(), SUB);
        operations.put(MUL.getSymbol(), MUL);
        operations.put(DIV.getSymbol(), DIV);
    }

    private BasicOperation(String symbol, BiFunction<Number, Number, Number> operation, boolean isUnary) {
        this.symbol = symbol;
        this.operation = operation;
        this.isUnary = isUnary;
    }

    @Override
    public Map<String, Operation> getOperations() {
        return this.operations;
    }


    @Override
    public Number calculate(Number a, Number b) {
        return operation.apply(a, b);
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isUnary() {
        return this.isUnary;
    }

    public static final BasicOperation ADD = new BasicOperation("+", (a, b)-> (a instanceof Double || b instanceof Double)? a.doubleValue()+b.doubleValue():a.longValue()+b.longValue(), false);
    public static final BasicOperation SUB = new BasicOperation("-", (a,b)-> (a instanceof Double || b instanceof Double)? a.doubleValue()-b.doubleValue():a.longValue()-b.longValue(), false);
    public static final BasicOperation MUL = new BasicOperation("*", (a,b)-> (a instanceof Double || b instanceof Double)? a.doubleValue()*b.doubleValue():a.longValue()*b.longValue(), false);
    public static final BasicOperation DIV = new BasicOperation("/", (a,b)-> {
        if(b.doubleValue()==0) throw new ArithmeticException("0으로 나눌 수 없습니다");
        return a.doubleValue() / b.doubleValue();
    }, false);

}
