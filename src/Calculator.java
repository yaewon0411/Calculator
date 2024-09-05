import ex.ExitException;
import util.CustomDesign;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Calculator {
    String firstNum;
    String secondNum;
    String operation;
    private static Scanner sc;
    private static final String BASIC_OPERATION_REG = "[+\\-*/]";
    private static final String ADVANCED_OPERATION_REG = "[√^!]|\\*\\*";

    private static final String BASIC = "basic";
    private static final String ADVANCED = "advanced";
    private String mode; //연산 모드 설정 -> 기본 산술 연산, 고급 연산

    //단항 연산자인지 판별
    private boolean isUnaryOperation(String operation) {
        if(this.mode.equals(ADVANCED))
            return !operation.equals("^");
        return false;
    }

    public void start(){
        this.mode = BASIC;
        CustomDesign.printWelcomeMessage();
        CustomDesign.printBasicOperations();
        sc = new Scanner(System.in);
        run();
    }
    public void run(){
        while(true) {
            try {
                boolean modeChanged = false;
                printInputHistory();

                // 첫 번째 피연산자
                System.out.print("숫자 입력 : ");
                firstNum = sc.nextLine();
                modeChanged = validateInput(firstNum, "operand");
                if(modeChanged){
                    reset();
                    continue;
                }

                printInputHistory();

                // 연산자
                System.out.print("연산자 입력 : ");
                operation = sc.nextLine();
                modeChanged = validateInput(operation, "operation");
                if(modeChanged){
                    reset();
                    continue;
                }
                printInputHistory();


                // 두 번째 피연산자 -> 이항 연산자의 경우에만 입력 받도록
                if (this.mode.equals(BASIC) || !isUnaryOperation(operation)) {
                    System.out.print("숫자 입력 : ");
                    secondNum = sc.nextLine();
                    modeChanged = validateInput(secondNum, "operand");
                    if(modeChanged){
                        reset();
                        continue;
                    }
                }
                printInputHistory();

                //계산
                Number result = calculate();
                showCalculateResult(result);
                reset();
            }catch(ExitException e){
                break;
            }catch(Exception e) {
                System.out.println(CustomDesign.ANSI_RED + "오류 발생: " + e.getMessage());
                System.out.println("=======처음부터 다시 시작합니다======"+CustomDesign.ANSI_RESET);
                reset();
            }
        }
        end();
    }

    //계산기 종료
    public void end(){
        System.out.println("***************계산기를 종료합니다***************");
        reset();
        sc.close();
    }

    //입력 값 초기화
    public void reset(){
        firstNum = "";
        secondNum = "";
        operation = null;
    }

    //입력 내역 출력
    public void printInputHistory() {
        String str = "[입력 내역 : ";
        if (firstNum != null) {
            str += firstNum;
            if(this.mode.equals(BASIC)) { //기본 산술 모드
                if (operation != null) {
                    str += (" " + operation);
                    if (secondNum != null) {
                        str += (" " + secondNum);
                    }
                }
            }
            else{ //고급 연산 모드
                if (operation != null) {
                    str += (" " + operation);
                    if (secondNum != null) {
                        str += (" " + secondNum);
                    }
                }
            }
        }
        System.out.println(CustomDesign.ANSI_GREEN +str + " ]" + CustomDesign.ANSI_RESET);
    }

    //exit 문구 검증
    public boolean isExit(String str){
        return "exit".equalsIgnoreCase(str);
    }

    //연산자 검증
    public void validateOperation(String operation) throws IllegalStateException{
        if(this.mode.equals(BASIC)) {
            if (!Pattern.matches(BASIC_OPERATION_REG, operation))
                throw new IllegalStateException("올바른 기본 연산자를 입력해주세요 (기본 연산자 : +, -, *, /)");
        }
        else{
            if (!Pattern.matches(ADVANCED_OPERATION_REG, operation))
                throw new IllegalStateException("올바른 고급 연산자를 입력해주세요 (고급 연산자 : √, ^, !, **)");
        }
    }


    //피연산자 검증
    public void validateOperand(String operand){
        try {
            Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            operand = "";
            throw new IllegalArgumentException("올바른 숫자를 입력해주세요");
        }
    }

    //고급 연산 문구 검증
    public boolean isAdvanced(String str){
        return ADVANCED.equalsIgnoreCase(str);
    }

    //기본 연산 문구 검증
    public boolean isBasic(String str){
        return BASIC.equalsIgnoreCase(str);
    }

    //모드 변경 -> 이전 입력 초기화
    private void switchMode(String newMode) {
        if (isAdvanced(newMode)) {
            this.mode = ADVANCED;
            CustomDesign.printAdvancedOperations();
        } else if (isBasic(newMode)) {
            this.mode = BASIC;
            CustomDesign.printBasicOperations();
        }
        reset();
    }

    //입력값 검증 -> 연산자 & 피연산자 검증 & 모드 변경 시 true 반환
    private boolean validateInput(String input, String type) throws IllegalArgumentException {
        if (isExit(input)) throw new ExitException();

        if(isAdvanced(input) || isBasic(input)) {
            switchMode(input);
            return true;
        }

        if (type.equals("operand"))
            validateOperand(input);
        else if (type.equals("operation"))
            validateOperation(input);

        return false;
    }

    //계산 -> 기본 산술 연산 수행
    private Number calculate() {
        Number a = parseNumber(firstNum);
        Number b;

        if (this.mode.equals(BASIC)) {
            b = parseNumber(secondNum);
            return switch (operation) {
                case "+" -> add(a, b);
                case "-" -> subtract(a, b);
                case "*" -> multiply(a, b);
                case "/" -> divide(a, b);
                default -> throw new IllegalStateException("올바른 연산자가 아닙니다: " + operation);
            };
        } else {
            if (!isUnaryOperation(operation)) {
                b = parseNumber(secondNum);
                return switch (operation) {
                    case "^" -> power(a, b);
                    default -> throw new IllegalStateException("올바른 고급 연산자가 아닙니다: " + operation);
                };
            } else {
                return switch (operation) {
                    case "√" -> squareRoot(a);
                    case "!" -> factorial(a);
                    case "**" -> square(a);
                    default -> throw new IllegalStateException("올바른 고급 연산자가 아닙니다: " + operation);
                };
            }
        }
    }
    private Number add(Number a, Number b) {
        return (a instanceof Double || b instanceof Double) ?
                a.doubleValue() + b.doubleValue() : a.longValue() + b.longValue();
    }

    private Number subtract(Number a, Number b) {
        return (a instanceof Double || b instanceof Double) ?
                a.doubleValue() - b.doubleValue() : a.longValue() - b.longValue();
    }

    private Number multiply(Number a, Number b) {
        return (a instanceof Double || b instanceof Double) ?
                a.doubleValue() * b.doubleValue() : a.longValue() * b.longValue();
    }

    private Number divide(Number a, Number b) {
        if (b.doubleValue() == 0) throw new ArithmeticException("0으로 나눌 수 없습니다 ");
        return a.doubleValue() / b.doubleValue();
    }

    private Number power(Number a, Number b) {
        if (a.doubleValue() == 0 && b.doubleValue() == 0)
            throw new IllegalStateException("0^0은 정의되지 않습니다");
        if (a.doubleValue() == 0 && b.doubleValue() < 0)
            throw new IllegalStateException("0을 음수 지수로 거듭제곱할 수 없습니다");
        return Math.pow(a.doubleValue(), b.doubleValue());
    }

    private Number squareRoot(Number a) {
        if (a.doubleValue() < 0)
            throw new IllegalStateException("제곱근은 음수에 대해 정의되지 않습니다");
        return Math.sqrt(a.doubleValue());
    }

    private Number factorial(Number a) {
        if (a.intValue() < 0) throw new IllegalStateException("음수는 팩토리얼 연산을 할 수 없습니다");
        if (a.intValue()> 20) throw new ArithmeticException("20 이하의 양수를 입력해주세요");
        return LongStream.rangeClosed(1, a.intValue()).reduce(1, (long x, long y) -> x * y);
    }

    private Number square(Number a) {
        return Math.pow(a.doubleValue(), 2);
    }

    //피연산자 String -> 숫자(정수/실수)로 파싱
    private Number parseNumber(String num) {
        if (num.contains("."))
            return Double.parseDouble(num);
        else {
            try {
                return Long.parseLong(num);
            } catch (NumberFormatException e) {
                return Double.parseDouble(num);
            }
        }
    }

    //계산 결과
    private void showCalculateResult(Number result) {
        System.out.printf(CustomDesign.ANSI_PINK + "========= 계산 결과 : %s =========\n"+CustomDesign.ANSI_RESET,
                (result.doubleValue() == result.intValue()) ?
                        String.valueOf(result.intValue()) :
                        String.valueOf(result.doubleValue()));
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.start();
    }
}
