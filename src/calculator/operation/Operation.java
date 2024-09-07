package calculator.operation;

import java.util.Map;

public interface Operation {

    Number calculate(Number a, Number b);

    String getSymbol();
    boolean isUnary();

    Map<String, Operation> getOperations();

}
