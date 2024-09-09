import ex.ExitException;
import util.CustomDesign;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Main {
    private static final String BASIC = "basic";
    private static final String ADVANCED = "advanced";
    private static final String BASIC_OPERATION_REG = "[+\\-*/]";
    private static final String ADVANCED_OPERATION_REG = "[√^!]|\\*\\*";
    public static void main(String[] args) {
            String firstNum = "";
            String secondNum = "";
            String operation = "";
            Scanner sc = new Scanner(System.in);
            String mode = BASIC;

            CustomDesign.printWelcomeMessage();
            CustomDesign.printBasicOperations();

            while (true) {
                try {
                    boolean modeChanged = false;
                    printInputHistory(firstNum, operation, secondNum);

                    // 첫 번째 피연산자 입력
                    System.out.print("숫자 입력: ");
                    firstNum = sc.nextLine();
                    modeChanged = validateInput(firstNum, "operand", mode);
                    if (modeChanged) {
                        firstNum = "";
                        secondNum = "";
                        operation = "";
                        if(mode.equals(BASIC)) mode = ADVANCED;
                        else mode = BASIC;
                        continue;
                    }

                    printInputHistory(firstNum, operation, secondNum);

                    // 연산자 입력
                    System.out.print("연산자 입력: ");
                    operation = sc.nextLine();
                    modeChanged = validateInput(operation, "operation", mode);
                    if (modeChanged) {
                        firstNum = "";
                        secondNum = "";
                        operation = "";
                        if(mode.equals(BASIC)) mode = ADVANCED;
                        else mode = BASIC;
                        continue;
                    }

                    printInputHistory(firstNum, operation, secondNum);

                    // 두 번째 피연산자 입력 (이항 연산자일 경우에만)
                    if (mode.equals(BASIC) || !isUnaryOperation(operation, mode)) {
                        System.out.print("숫자 입력: ");
                        secondNum = sc.nextLine();
                        modeChanged = validateInput(secondNum, "operand", mode);
                        if (modeChanged) {
                            firstNum = "";
                            secondNum = "";
                            operation = "";
                            if(mode.equals(BASIC)) mode = ADVANCED;
                            else mode = BASIC;
                            continue;
                        }
                        printInputHistory(firstNum, operation, secondNum);
                    }

                    //계산 수행
                    Number result = calculate(firstNum, secondNum, operation, mode);
                    printCalculateResult(result);


                    firstNum = "";
                    secondNum = "";
                    operation = "";
                } catch (ExitException e) {
                    break;
                } catch (Exception e) {
                    System.out.println(CustomDesign.ANSI_RED + "오류 발생: " + e.getMessage());
                    System.out.println("=======처음부터 다시 시작합니다======"+CustomDesign.ANSI_RESET);
                    firstNum = "";
                    secondNum = "";
                    operation = "";
                }
            }

            //종료
            System.out.println("***************계산기를 종료합니다***************");
            sc.close();
        }

        //모드가 고급일 경우 단항 연산자인지 확인
        private static boolean isUnaryOperation(String operation, String mode) {
            return mode.equals(ADVANCED) && !operation.equals("^");
        }

        //입력 내역 출력
        private static void printInputHistory(String firstNum, String operation, String secondNum) {
            String str = "[입력 내역 : " + firstNum;
            if (!operation.isEmpty()) str += " " + operation;
            if (!secondNum.isEmpty()) str += " " + secondNum;
            System.out.println(CustomDesign.ANSI_GREEN +str + " ]" + CustomDesign.ANSI_RESET);
        }

        //연산 모드 전환 -> 전환 시 안내 문구 출력
        private static boolean validateInput(String input, String type, String mode) throws IllegalArgumentException {
            if ("exit".equalsIgnoreCase(input)) throw new ExitException();

            if (ADVANCED.equalsIgnoreCase(input)) {
                CustomDesign.printAdvancedOperations();
                return true;
            }
            else if (BASIC.equalsIgnoreCase(input)) {
                CustomDesign.printBasicOperations();
                return true;
            }

            if ("operand".equals(type)) {
                try {
                    Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("올바른 숫자를 입력해주세요");
                }
            } else if ("operation".equals(type)) {
                if (mode.equals(BASIC) && !Pattern.matches(BASIC_OPERATION_REG, input)) {
                    throw new IllegalArgumentException("올바른 기본 연산자를 입력해주세요 (기본 연산자: +, -, *, /)");
                } else if (mode.equals(ADVANCED) && !Pattern.matches(ADVANCED_OPERATION_REG, input)) {
                    throw new IllegalArgumentException("올바른 고급 연산자를 입력해주세요 (고급 연산자: √, ^, !, **)");
                }
            }

            return false;
        }

        //계산 수행
        private static Number calculate(String firstNum, String secondNum, String operation, String mode) {
            Number a = parseNumber(firstNum);
            Number b;

            if (mode.equals(BASIC)) {
                b = parseNumber(secondNum);
                return switch (operation) {
                    case "+" -> add(a, b);
                    case "-" -> subtract(a, b);
                    case "*" -> multiply(a, b);
                    case "/" -> divide(a, b);
                    default -> throw new IllegalStateException("올바른 연산자가 아닙니다: " + operation);
                };
            } else {
                if (!isUnaryOperation(operation, mode)) {
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

        //피연산자 파싱
        private static Number parseNumber(String num) {
            return num.contains(".") ? Double.parseDouble(num) : Long.parseLong(num);
        }

        // ======기본 산술 연산======
        private static Number add(Number a, Number b) { return a.doubleValue() + b.doubleValue(); }
        private static Number subtract(Number a, Number b) { return a.doubleValue() - b.doubleValue(); }
        private static Number multiply(Number a, Number b) { return a.doubleValue() * b.doubleValue(); }
        private static Number divide(Number a, Number b) {
            if (b.doubleValue() == 0) throw new ArithmeticException("0으로 나눌 수 없습니다");
            return a.doubleValue() / b.doubleValue();
        }

        //=====고급 산술 연산======
        private static Number power(Number a, Number b) {
            if (a.doubleValue() == 0 && b.doubleValue() == 0) throw new IllegalStateException("0^0은 정의되지 않습니다");
            if(a.doubleValue()==0 && b.doubleValue()<0) throw new IllegalStateException("0을 음수 지수로 거듭제곱할 수 없습니다");
            return Math.pow(a.doubleValue(), b.doubleValue());
        }
        private static Number squareRoot(Number a) {
            if (a.doubleValue() < 0) throw new IllegalStateException("제곱근은 음수에 대해 정의되지 않습니다");
            return Math.sqrt(a.doubleValue());
        }
        private static Number factorial(Number a) {
            if (a.intValue() < 0) throw new IllegalStateException("음수는 팩토리얼 연산을 할 수 없습니다");
            if(a.intValue()>20) throw new ArithmeticException("20 이하의 양수를 입력해주세요");
            return LongStream.rangeClosed(1, a.intValue()).reduce(1, (long x, long y) -> x * y);
        }
        private static Number square(Number a) { return Math.pow(a.doubleValue(), 2); }

        // 결과 출력
        private static void printCalculateResult(Number result) {
            System.out.printf(CustomDesign.ANSI_PINK+"========= 계산 결과 : %s =========\n"+CustomDesign.ANSI_RESET, result.doubleValue() == result.intValue() ? result.intValue() : result.doubleValue());
        }

}