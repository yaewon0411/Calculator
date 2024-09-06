package calculator.operation.advanced;

import calculator.operation.Operation;

import java.util.function.BiFunction;
import java.util.stream.LongStream;

/*
<고급 연산>

제곱근(√x)
거듭 제곱 (x^y) -> 얘만 이항 연산자
팩토리얼 (x!)
제곱 (x^2)
* */
public class AdvancedOperation implements Operation {
    private String symbol;
    private BiFunction<Number, Number, Number> operation;

    private boolean isUnary; //단항 연산자이면 true, 아니면 false

    public AdvancedOperation(String symbol, BiFunction<Number, Number, Number> operation, boolean isUnary) {
        this.symbol = symbol;
        this.operation = operation;
        this.isUnary = isUnary;
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

    public static final AdvancedOperation SQUARE_ROOT = new AdvancedOperation("√", (a, b) -> {
        if(a.doubleValue()<0)
            throw new IllegalStateException("제곱근은 음수에 대해 정의되지 않습니다");
        return Math.sqrt(a.doubleValue());
    }, true);

    public static final AdvancedOperation POWER = new AdvancedOperation("^", (a, b) -> {
        if(a.doubleValue()==0 && b.doubleValue()==0)throw new IllegalStateException("0^0은 정의되지 않습니다");
        if(a.doubleValue()==0 && b.doubleValue()<0)
            throw new IllegalStateException("0을 음수 지수로 거듭제곱할 수 없습니다");
        return Math.pow(a.doubleValue(), b.doubleValue());
    }, false);

    public static final AdvancedOperation FACTORIAL = new AdvancedOperation("!", (a,b) -> {
        if(a.intValue() < 0) throw new IllegalStateException("음수는 팩토리얼 연산을 할 수 없습니다");
        if(a.intValue()>20) throw new ArithmeticException("20 이하의 양수를 입력해주세요");

        return LongStream.rangeClosed(1, a.intValue()).reduce(1, (long x, long y) -> x*y);
    }, true);

    public static final AdvancedOperation SQUARE = new AdvancedOperation("**", (a,b) -> Math.pow(a.doubleValue(), 2), true);
}
