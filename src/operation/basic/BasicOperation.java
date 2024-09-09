package operation.basic;

import operation.Operation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

//기본 산술 연산자: 덧셈, 뺄셈, 곱셈, 나눗셈
public class BasicOperation<T extends Number> implements Operation<T> {

    private String symbol;
    private BiFunction<T, T, T> operation;
    private boolean isUnary; //단항 연산자이면 true, 아니면 false


    private final Map<String, Operation<T>> operations = new HashMap<>();

    public BasicOperation(){
        operations.put(ADD.getSymbol(), (Operation<T>) ADD);
        operations.put(SUB.getSymbol(), (Operation<T>) SUB);
        operations.put(MUL.getSymbol(), (Operation<T>) MUL);
        operations.put(DIV.getSymbol(), (Operation<T>) DIV);
    }

    private BasicOperation(String symbol, BiFunction<T,T,T> operation, boolean isUnary) {
        this.symbol = symbol;
        this.operation = operation;
        this.isUnary = isUnary;
    }

    @Override
    public Map<String, Operation<T>> getOperations() {
        return this.operations;
    }

    @Override
    public T calculate(T a, T b) {
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

    @SuppressWarnings("unchecked") //타입 캐스팅 경고 억제
    private static <T extends Number> T convertToT(Number n, Class<T> type) {
        if (Double.class.equals(type)) {
            return (T) Double.valueOf(n.doubleValue());
        } else if (Long.class.equals(type)) {
            return (T) Long.valueOf(n.longValue());
        } else if (Integer.class.equals(type)){
            return (T) Integer.valueOf(n.intValue());
        } else {
            throw new IllegalArgumentException("다음 타입은 지원하지 않습니다: " + type);
        }
    }

    public static final BasicOperation<Number> ADD = new BasicOperation<>("+",
            (a, b) -> {
                Class<? extends Number> resultType =
                        a instanceof Double || b instanceof Double ? Double.class :
                                a instanceof Long || b instanceof Long ? Long.class : Integer.class;
                return convertToT(a.doubleValue() + b.doubleValue(), resultType);
            }, false
    );
    public static final BasicOperation<Number> SUB = new BasicOperation<>("-",
            (a, b) -> {
                Class<? extends Number> resultType =
                        a instanceof Double || b instanceof Double ? Double.class :
                                a instanceof Long || b instanceof Long ? Long.class : Integer.class;
                return convertToT(a.doubleValue() - b.doubleValue(), resultType);
            }, false
    );

    public static final BasicOperation<Number> MUL = new BasicOperation<>("*",
            (a, b) -> {
                Class<? extends Number> resultType =
                        a instanceof Double || b instanceof Double ? Double.class :
                                a instanceof Long || b instanceof Long ? Long.class : Integer.class;
                return convertToT(a.doubleValue() * b.doubleValue(), resultType);
            }, false
    );

    public static final BasicOperation<Number> DIV = new BasicOperation<>("/",
            (a, b) -> {
                if (b.doubleValue() == 0) throw new ArithmeticException("0으로 나눌 수 없습니다.");
                return convertToT(a.doubleValue() / b.doubleValue(), Double.class);
            }, false
    );

}
