package operation;

import java.util.Map;

public interface Operation<T extends Number> {

    //실제 연산 수행
    T calculate(T a, T b);
    //연산자 가져오기
    String getSymbol();
    //단항 연산자이면 true
    boolean isUnary();

    //연산 모드 별 연산자 목록 가져오기
    Map<String, Operation<T>> getOperations();
}
