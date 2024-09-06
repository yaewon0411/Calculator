package calculator.operation;

public interface Operation {

    Number calculate(Number a, Number b);

    String getSymbol();
    boolean isUnary();
}
