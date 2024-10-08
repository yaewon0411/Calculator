package calculator;

import operation.OperationMode;
import operation.OperationStrategy;
import operation.advanced.AdvancedOperation;
import operation.basic.BasicOperation;
import ex.ExitException;
import util.CustomDesign;
import java.util.Scanner;

public class Calculator<T extends Number> {
    private CalculatorState calculatorState; //계산기 현재 상태 관리
    private static Scanner sc;//스캐너
    private OperationStrategy<T> operationStrategy; //연산 전략 관리
    private boolean isHistoryMode; //연산 결과 내역 보기

    public Calculator() {
        operationStrategy = new OperationStrategy<>();
        calculatorState = new CalculatorState();
        sc = new Scanner(System.in);
    }

    //계산기 시작!!
    public void start() {
        CustomDesign.printWelcomeMessage();
        CustomDesign.printBasicOperations();
        isHistoryMode = false;
        run();
    }

    //계산기 전체 입력 처리
    private boolean handleInput(){ //모드 변환 or 입력을 다 받으면 true 리턴해서 while 반복문 다시 돌아가도록

        if(isHistoryMode){ //연산 기록을 보고 있는 상태라면
            System.out.print("명령어를 입력하세요 : ");
            String input = sc.nextLine().trim();
            validateInput(input, null);
            return true;
        }

        printInputHistory();

        if(calculatorState.getFirstNum()== null)
            return handleOperandInput("첫 번째");

        if(calculatorState.getOperation()==null)
            return handleOperationInput();

        if(!operationStrategy.isUnary(calculatorState.getOperation()) && calculatorState.getSecondNum()==null)
            return handleOperandInput("두 번째");

        return false;
    }
    //입력 중 연산자 처리
    private boolean handleOperationInput(){
        System.out.print("연산자 입력 : ");
        String operation = sc.nextLine().trim();
        boolean modeChanged = validateInput(operation, "operation");
        if(modeChanged){
            reset();
            return true;
        }
        return false;
    }
    //입력 중 피연산자 처리
    private boolean handleOperandInput(String order){
        System.out.print(order+" 숫자 입력 : ");
        String input = sc.nextLine().trim();
        boolean modeChanged = validateInput(input, "operand");
        if(modeChanged){
            reset();
            return true;
        }
        if(calculatorState.getFirstNum()==null) calculatorState.setFirstNum(input);
        else calculatorState.setSecondNum(input);

        return false;
    }
    //실제 작동
    private <T extends Number>void run(){
        while(true) {
            try {
                if(handleInput()) continue; //모드 변환 시 다시 시작
                //계산
                if(calculatorState.isReadyToCalculate(operationStrategy)) {
                    printInputHistory();
                    Number result = calculate();
                    printCalculateResult(result);
                    reset();
                }
            }catch(ExitException e){
                calculatorState.clearAllHistory();
                break;
            }catch(Exception e) {
                handleError(e);
            }
        }
        end();
    }
    //에러 문구 처리 및 입력값 초기화
    private void handleError(Exception e){
        System.out.println(CustomDesign.ANSI_RED + "오류 발생: " + e.getMessage());
        if(isHistoryMode){
            CustomDesign.printAllHistory(calculatorState);
        }
        else {
            System.out.println("=======처음부터 다시 시작합니다======" + CustomDesign.ANSI_RESET);
            reset();
        }
    }

    //계산기 종료
    private void end(){
        CustomDesign.printExitMessage();
        reset();
        calculatorState.clearAllHistory();
        sc.close();
    }

    //입력 값 초기화
    private void reset(){
        calculatorState.reset();
    }

    //입력 내역 출력
    private void printInputHistory() {
        System.out.println(CustomDesign.ANSI_GREEN + calculatorState + CustomDesign.ANSI_RESET);
    }


    //exit 문구 검증
    private boolean isExit(String str){
        return "exit".equalsIgnoreCase(str);
    }

    //연산자 검증
    private void validateOperation(String operation){
        if(operationStrategy.getOperation(operation)==null) throw new IllegalArgumentException("존재하지 않는 연산자 입니다");
        calculatorState.setOperation(operation);
    }

    //피연산자 검증
    private void validateOperand(String operand){
        try {
            Double.parseDouble(operand);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 숫자를 입력해주세요");
        }
    }

    //고급 연산 문구 검증
    private boolean isAdvanced(String str){
        return OperationMode.ADVANCED.toString().equalsIgnoreCase(str);
    }

    //기본 연산 문구 검증
    private boolean isBasic(String str){
        return OperationMode.BASIC.toString().equalsIgnoreCase(str);
    }

    //모드 변경 -> 이전 입력 초기화
    private void switchMode(String newMode) {
        if (isAdvanced(newMode)) {
            operationStrategy.setOperation(new AdvancedOperation<>());
            CustomDesign.printAdvancedOperations();
        } else if (isBasic(newMode)) {
            operationStrategy.setOperation(new BasicOperation<>());
            CustomDesign.printBasicOperations();
        }
        reset();
    }

    private boolean isHistory(String input){
        return "history".equalsIgnoreCase(input);
    }
    private void printAllHistory(){
        CustomDesign.printAllHistory(calculatorState);
    }

    //입력값 검증 -> 연산자 & 피연산자 검증 & 모드 변경 시 & history 입력 시 true 반환
    private boolean validateInput(String input, String type) throws IllegalArgumentException {
        if (isExit(input)) throw new ExitException();

        if(isAdvanced(input) || isBasic(input)) {
            isHistoryMode = false;
            switchMode(input);
            return true;
        }

        if(isHistory(input)){ //만약 history를 쳤다면 -> 연산 내역 보여주도록
            printAllHistory();
            isHistoryMode = true;
            return true;
        }

        if(isHistoryMode){
            if("remove".equalsIgnoreCase(input)) { //history 모드에서 remove를 입력하면 가장 오래전 결과 삭제
                handleHistoryRemove();
                return true;
            }
            else if("greater".equalsIgnoreCase(input)){ //history 모드에서 입력한 값보다 큰 결과를 보기 원할 때
                handleGreaterThan();
                return true;
            }
            else throw new IllegalArgumentException("올바른 명령어를 입력해주세요");
        }

        if(type != null) {
            if (type.equals("operand"))
                validateOperand(input);
            else if (type.equals("operation"))
                validateOperation(input);
        }

        return false;
    }
    private void handleGreaterThan(){
        System.out.print("숫자를 입력해주세요 : ");
        String input = sc.nextLine().trim();
        try {
            double threshold = Double.parseDouble(input);
            CustomDesign.printResultsGreaterThan(calculatorState.getResultsGreaterThan(threshold), threshold);
        }catch (NumberFormatException e){
            throw new NumberFormatException("올바른 숫자를 입력해주세요");
        }
    }

    //가장 오래된 연산 결과 삭제 후 다시 내역 출력
    private void handleHistoryRemove(){
        boolean isRemoved = calculatorState.removeOldestResult();
        if(isRemoved) System.out.println("삭제 되었습니다.");
        else System.out.println("삭제할 연산 결과가 없습니다.");
        CustomDesign.printAllHistory(calculatorState);
    }

    //계산
    private Number calculate(){
        T a = parseNumber(calculatorState.getFirstNum());
        T b = operationStrategy.isUnary(calculatorState.getOperation())?null:parseNumber(calculatorState.getSecondNum());
        Number result = operationStrategy.calculate(calculatorState.getOperation(), a, b);
        //계산 결과 저장
        calculatorState.getCalculatedResults().add(result);
        return result;
    }

    //피연산자 String -> 숫자(정수/실수)로 파싱
    private T parseNumber(String num) {
        if (num.contains(".")) {
            return (T) Double.valueOf(num);
        } else {
            try {
                return (T) Long.valueOf(num);
            } catch (NumberFormatException e) {
                return (T) Double.valueOf(num);
            }
        }
    }

    //계산 결과
    private void printCalculateResult(Number result) {
        System.out.printf(CustomDesign.ANSI_PINK + "========= 계산 결과 : %s =========\n"+CustomDesign.ANSI_RESET,
                (result.doubleValue() == result.longValue()) ?
                        String.valueOf(result.longValue()) :
                        String.valueOf(result.doubleValue()));
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.start();
    }

}
