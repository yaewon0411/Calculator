package calculator.operation;

import java.util.Arrays;
import java.util.stream.LongStream;

/*
<고급 연산>

제곱근(√x)
거듭 제곱 (x^y)
팩토리얼 (x!)
제곱 (x^2)
* */
public enum AdvancedOperation {
    SQUARE_ROOT("√"){
        public Number calculate(Number a, Number b){
            if(a.doubleValue()<0)
                throw new IllegalStateException("제곱근은 음수에 대해 정의되지 않습니다");
            return Math.sqrt(a.doubleValue());
        }
    },
    POWER("^"){
        @Override
        public Number calculate(Number a, Number b) {
            if(a.doubleValue()==0 && b.doubleValue()==0)
                throw new IllegalStateException("0^0은 정의되지 않습니다");
            if(a.doubleValue()==0 && b.doubleValue()<0)
                throw new IllegalStateException("0을 음수 지수로 거듭제곱할 수 없습니다");
            return Math.pow(a.doubleValue(), b.doubleValue());
        }
    },
    FACTORIAL("!"){
        public Number calculate(Number a, Number b){
            return factorial(a.intValue());
        }
    },
    SQUARE("**"){
        public Number calculate(Number a, Number b){
            return Math.pow(a.doubleValue(), 2);
        }
    }
    ;
    private final String symbol;

    AdvancedOperation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
    public static AdvancedOperation getAdvancedOperation(String symbol){
        return Arrays.stream(values())
                .filter(o -> o.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("올바른 고급 연산자를 입력해주세요 (고급 연산자 : √, ^, !, **) : " + symbol);)
    }

    private static long factorial(int a){
        if(a < 0) throw new IllegalStateException("음수는 팩토리얼 연산을 할 수 없습니다");
        if(a>20) throw new ArithmeticException("20 이하의 양수를 입력해주세요");

        return LongStream.rangeClosed(1, a).reduce(1, (long x, long y) -> x*y);
    }

    public abstract Number calculate(Number a, Number b);
}
