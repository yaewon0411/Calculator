# 1. 클래스 없는 계산기 구현
## 개요
별도의 계산기 클래스 없이 기본 및 고급 산술 연산을 수행하는 콘솔 기반 계산기 애플리케이션입니다.<br>
모든 로직이 Main 클래스 내에 구현되어 있어 단일 파일로 작동하며 간단하면서도 기능적인 연산을 제공합니다.<br>

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


# 2. 클래스를 적용한 계산기 구현
## 개요
객체 지향 프로그래밍 원칙을 적용한 콘솔 기반 계산기 애플리케이션입니다<br>
기본적인 산술 연산부터 고급 수학 연산까지 지원하며, 연산 모드 전환과 계산 기록 관리 기능을 제공합니다.

## 주요 기능
- 기본 산술 연산 (덧셈, 뺄셈, 곱셈, 나눗셈)
- 고급 수학 연산 (제곱근, 거듭제곱, 팩토리얼, 제곱)
- 연산 모드 전환 (기본/고급)
- 계산 기록 관리 및 조회
- 오류 처리 및 예외 상황 관리

## 구현 세부 사항
### 클래스 구조
- `Calculator`: 메인 계산기 클래스로, 사용자 입력 처리 및 전반적인 로직 관리
- `CalculatorState`: 계산기의 현재 상태 (입력된 숫자, 연산자, 계산 결과) 관리
- `OperationStrategy`: 연산 전략 관리 (기본/고급 연산 모드 전환)
- `Operation`: 연산 인터페이스
- `BasicOperation`: 기본 산술 연산 구현
- `AdvancedOperation`: 고급 수학 연산 구현

### 디자인 패턴
- 전략 패턴: 연산 모드를 쉽게 전환할 수 있도록 OperationStrategy 사용
- 인터페이스 분리 원칙: Operation 인터페이스를 통해 다양한 연산 구현

### 주요 메서드
- Calculator.start(): 계산기 시작
- Calculator.handleInput(): 사용자 입력 처리
- Calculator.calculate(): 실제 계산 수행
- CalculatorState.isReadyToCalculate(): 계산 준비 상태 확인
- OperationStrategy.calculate(): 선택된 연산 전략에 따라 계산 수행

## 사용 방법
1. 프로그램 실행
2. 기본 모드에서 시작:
    - 첫 번째 숫자 입력
    - 연산자 입력
    - 두 번째 숫자 입력 (단항 연산자의 경우 생략)
3. 고급 모드 전환: "advanced" 입력
4. 기본 모드로 돌아가기: "basic" 입력
5. 계산 기록 보기: "history" 입력
    - 가장 오래된 결과 삭제: "remove" 입력
    - 특정 값보다 큰 결과 보기: "greater" 입력 후 기준값 입력
6. 프로그램 종료: "exit" 입력

## 오류 처리
- 잘못된 입력에 대한 예외 처리
- 0으로 나누기 등 수학적 오류 처리

## 확장성
- 새로운 연산 추가 용이 (Operation 인터페이스 구현)
- 다양한 숫자 타입 지원 (제네릭 사용)




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
