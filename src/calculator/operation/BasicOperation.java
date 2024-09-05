package calculator.operation;

//기본 산술 연산자: 덧셈, 뺄셈, 곱셈, 나눗셈
public enum BasicOperation {
    ADD("+"){
        public Number calculate(Number a, Number b){
            return add(a,b);
        }
    },
    SUBTRACT("-"){
        public Number calculate(Number a, Number b){
            return sub(a, b);
        }
    },
    MULTIPLY("*"){
        public Number calculate(Number a, Number b){
            return mul(a, b);
        }
    },
    DIVIDE("/"){
        public Number calculate(Number a, Number b){
            return div(a, b);
        }
    };
    private final String symbol;

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static BasicOperation getBasicOperation(String symbol){
        for (BasicOperation basicOperation : values()) {
            if(basicOperation.symbol.equals(symbol)) return basicOperation;
        }
        throw new IllegalStateException("올바른 기본 연산자를 입력해주세요 (기본 연산자 : +, -, *, /) : "+symbol);
    }

    private static Number add(Number a, Number b){
        if(a instanceof Double || b instanceof Double)
            return a.doubleValue() + b.doubleValue();
        else
            return a.longValue() + b.longValue();
    }

    private static Number sub(Number a, Number b){
        if(a instanceof Double || b instanceof Double)
            return a.doubleValue() - b.doubleValue();
        else
            return a.longValue() - b.longValue();
    }

    private static Number mul(Number a, Number b){
        if(a instanceof Double || b instanceof Double)
            return a.doubleValue() * b.doubleValue();
        else
            return a.longValue() * b.longValue();
    }

    private static Number div(Number a, Number b){
        if(b.doubleValue()==0) throw new ArithmeticException("0으로 나눌 수 없습니다");
        return a.doubleValue() / b.doubleValue();
    }
    public abstract Number calculate(Number a, Number b);


}
