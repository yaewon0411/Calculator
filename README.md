# 1. 계산기 없는 클래스 구현하기
## 개요
별도의 계산기 클래스 없이 기본 및 고급 산술 연산을 수행하는 콘솔 기반 계산기 애플리케이션입니다.<br>
모든 로직이 Main 클래스 내에 구현되어 있어, 단일 파일로 작동하는 간단하면서도 기능적인 계산기를 제공합니다.<br>

## 주요 기능
- 기본 산술 연산 (덧셈, 뺄셈, 곱셈, 나눗셈)
- 고급 산술 연산 (제곱근, 거듭제곱, 팩토리얼, 제곱)
- 모드 전환 (기본 모드 ↔ 고급 모드)
- 실시간 입력 내역 표시
- 오류 처리 및 예외 상황 관리

## 구현 세부사항
- 단일 클래스 구조: 모든 로직이 Main 클래스 내에 구현되어 있습니다.
- 모드 관리: BASIC과 ADVANCED 두 가지 모드를 지원합니다.
- 입력 검증: 정규 표현식을 사용하여 유효한 연산자 입력을 검증합니다.
- 동적 타입 처리: Number 클래스를 사용하여 정수와 실수 연산을 동적으로 처리합니다.
= 사용자 친화적 인터페이스: CustomDesign 클래스를 활용하여 컬러풀한 콘솔 출력을 제공합니다.

## 주요 메서드
- `main()`: 프로그램의 메인 루프를 관리합니다.
- `validateInput()`: 사용자 입력을 검증하고 모드 전환을 처리합니다.
- `calculate()`: 입력된 연산을 수행합니다.
- `isUnaryOperation()`: 단항 연산자 여부를 확인합니다.
- 각종 산술 연산 메서드 (add, subtract, multiply, divide, power, squareRoot, factorial, square)

## 사용 방법
1. 프로그램을 실행합니다.
2. 기본 모드에서 시작하며, 첫 번째 숫자를 입력합니다.
3. 연산자를 입력합니다.
4. 필요한 경우 두 번째 숫자를 입력합니다.
5. 결과가 표시됩니다.
6. "advanced"를 입력하여 고급 모드로 전환하거나, "basic"을 입력하여 기본 모드로 돌아갈 수 있습니다.
7. "exit"를 입력하여 프로그램을 종료합니다.


# 2. 


### 기록
#### 1) ENUM 클래스의 values()
- enum 클래스에 자동으로 생성되는 메서드
- enum 타입의 모든 상수를 배열로 반환 -> 즉, 해당 enum 클래스가 정의한 모든 상수들을 배열로 만들어 제공
- Arrays.stream(values()) 를 통해 values()가 반환한 배열을 스트림으로 변환한 후 enum 상수 필터링 가능
```java
public static AdvancedOperation getAdvancedOperation(String symbol){
    return Arrays.stream(values())
            .filter(o -> o.symbol.equals(symbol))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("올바른 고급 연산자를 입력해주세요 (고급 연산자 : √, ^, !, **) : " + symbol));
}
```