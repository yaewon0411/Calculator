package operation.advanced;

import operation.Operation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

/*
<고급 연산>

제곱근(√x)
거듭 제곱 (x^y) -> 얘만 이항 연산자
팩토리얼 (x!)
제곱 (x^2)
* */
public class AdvancedOperation<T extends Number> implements Operation<T> {
    private String symbol;
    private BiFunction<T, T, T> operation;


    private final Map<String, Operation<T>> operations = new HashMap<>();

    private boolean isUnary; //단항 연산자이면 true, 아니면 false

    public AdvancedOperation() {
        operations.put(SQUARE.getSymbol(), (Operation<T>) SQUARE);
        operations.put(SQUARE_ROOT.getSymbol(), (Operation<T>)SQUARE_ROOT);
        operations.put(POWER.getSymbol(), (Operation<T>)POWER);
        operations.put(FACTORIAL.getSymbol(), (Operation<T>)FACTORIAL);
    }

    private AdvancedOperation(String symbol, BiFunction<T, T, T> operation, boolean isUnary) {
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

    public static final AdvancedOperation<Number> SQUARE_ROOT = new AdvancedOperation<>("√",
            (a, b) -> {
                if (a.doubleValue() < 0)
                    throw new IllegalStateException("제곱근은 음수에 대해 정의되지 않습니다");
                return convertToT(Math.sqrt(a.doubleValue()), Double.class);
            }, true);


    public static final AdvancedOperation<Number> POWER = new AdvancedOperation<>("^",
            (a, b) -> {
                double aVal = a.doubleValue();
                double bVal = b.doubleValue();
                if (aVal == 0 && bVal == 0) throw new IllegalStateException("0^0은 정의되지 않습니다");
                if (aVal == 0 && bVal < 0)
                    throw new IllegalStateException("0을 음수 지수로 거듭제곱할 수 없습니다");
                Class<? extends Number> resultType = a instanceof Double || b instanceof Double ? Double.class : Long.class;
                return convertToT(Math.pow(aVal, bVal), resultType);
            }, false);

    public static final AdvancedOperation<Number> FACTORIAL = new AdvancedOperation<>("!",
            (a, b) -> {
                int aInt = a.intValue();
                if (aInt < 0) throw new IllegalStateException("음수는 팩토리얼 연산을 할 수 없습니다");
                if (aInt > 20) throw new ArithmeticException("20 이하의 양수를 입력해주세요");
                long result = LongStream.rangeClosed(1, aInt).reduce(1, (x, y) -> x * y);
                return convertToT(result, Long.class);
            }, true);

    public static final AdvancedOperation<Number> SQUARE = new AdvancedOperation<>("**",
            (a, b) -> {
                double result = Math.pow(((Number)a).doubleValue(), 2);
                return (a instanceof Double ? convertToT(result, Double.class) : convertToT(result, Long.class));
            }, true);

}
