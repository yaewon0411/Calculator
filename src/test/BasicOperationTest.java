package test;

import calculator.operation.BasicOperation;
import ex.ExitException;
import util.CustomDesign;

import java.util.Scanner;

public class BasicOperationTest {
    String firstNum;
    String secondNum;
    BasicOperation basicOperation;
    private Scanner sc;

    public void start(){
        CustomDesign.printWelcomeMessage();
        sc = new Scanner(System.in);
    }

    public void end(){
        System.out.println("계산기를 종료합니다");
        reset();
        sc.close();
    }

    public void reset(){
        firstNum = "";
        secondNum = "";
        basicOperation = null;
    }

    //입력 내역 출력
    public void showInputHistory() {
        String str = "[입력 내역 : ";
        if (firstNum != null) {
            str += firstNum;
            if (basicOperation != null) {
                str += (" " + basicOperation.getSymbol());
                if (secondNum != null) {
                    str += (" " + secondNum);
                }
            }
        }
        System.out.println(str + "]");
    }

    //exit 문구 검증
    public boolean isExit(String str){
        return "exit".equalsIgnoreCase(str);
    }

    //연산자 검증
    public void validateOperation(String operation) throws IllegalStateException{
        basicOperation = BasicOperation.getBasicOperation(operation);
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

    //입력값 검증 -> 연산자 검증, 피연산자 검증
    private void validateInput(String input, String type) throws IllegalArgumentException {
        if (isExit(input)) throw new ExitException();

        if (type.equals("operand"))
            validateOperand(input);
        else if (type.equals("operation"))
            validateOperation(input);
    }

    //계산 -> 기본 산술 연산 수행
    private Number calculate(){
        return basicOperation.calculate(parseNumber(firstNum),parseNumber(secondNum));
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
        System.out.printf("======= 계산 결과 : %s =======\n",
                (result.doubleValue() == result.intValue()) ?
                        String.valueOf(result.intValue()) :
                        String.valueOf(result.doubleValue()));
    }

    public void 정수_기본산술연산_테스트(){
        start();

        while(true) {
            try {
                //첫 번째 피연산자
                System.out.print("숫자 입력 : ");
                firstNum = sc.nextLine();
                validateInput(firstNum, "operand");
                showInputHistory();

                //연산자
                System.out.print("연산자 입력 (+, -, *, /) : ");
                String operation = sc.nextLine();
                validateInput(operation, "operation");
                showInputHistory();

                //두 번째 피연산자
                System.out.print("숫자 입력 : ");
                secondNum = sc.nextLine();
                validateInput(secondNum, "operand");
                showInputHistory();

                //계산
                Number result = calculate();
                showCalculateResult(result);
                reset();
            }catch(ExitException e){
                break;
             }catch(Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
                System.out.println("=======처음부터 다시 시작합니다======");
                reset();
            }
        }
        end();
    }


    public static void main(String[] args) {
        BasicOperationTest b = new BasicOperationTest();
        b.정수_기본산술연산_테스트();
    }
}
