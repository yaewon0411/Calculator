package util;

import test.AdvancedOperation;

public class CustomDesign {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public static final String ANSI_PINK = "\u001B[35m";

    public static void printWelcomeMessage() {
        System.out.println(ANSI_YELLOW);
        System.out.println("    _____      _            _       _             ");
        System.out.println("   / ____|    | |          | |     | |            ");
        System.out.println("  | |     __ _| | ___ _   _| | __ _| |_ ___  _ __ ");
        System.out.println("  | |    / _` | |/ __| | | | |/ _` | __/ _ \\| '__|");
        System.out.println("  | |___| (_| | | (__| |_| | | (_| | || (_) | |   ");
        System.out.println("   \\_____\\__,_|_|\\___|\\__,_|_|\\__,_|\\__\\___/|_|   ");
        System.out.println(ANSI_RESET);
    }
    public static void printBasicOperations(){
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "       지원하는 기본 산술 연산 목록" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println("            " + ANSI_YELLOW + "+ " + ANSI_RESET + "더하기");
        System.out.println("            " + ANSI_YELLOW + "- " + ANSI_RESET + "빼기");
        System.out.println("            " + ANSI_YELLOW + "* " + ANSI_RESET + "곱하기");
        System.out.println("            " + ANSI_YELLOW + "/ " + ANSI_RESET + "나누기");
        System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
        System.out.println("    " + ANSI_GREEN + "'advanced'를 입력하면 고급 연산을 수행할 수 있습니다." + ANSI_RESET);
        System.out.println("    " + ANSI_RED + "종료를 원하시면 'exit'을 입력해주세요" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println();
    }

    public static void printAdvancedOperations() {
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "       지원하는 고급 산술 연산 목록" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);

        for (AdvancedOperation op : AdvancedOperation.values()) {
            String description = getOperationDescription(op);
            System.out.println("            " + ANSI_YELLOW + op.getSymbol() + ANSI_RESET + " : " + description);
        }

        System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
        System.out.println("    " + ANSI_GREEN + "'basic'을 입력하면 기본 연산으로 돌아갑니다." + ANSI_RESET);
        System.out.println("    " + ANSI_RED + "종료를 원하시면 'exit'을 입력해주세요" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println();
    }


    private static String getOperationDescription(AdvancedOperation op) {
        switch (op) {
            case SQUARE_ROOT: return "제곱근 (음수 입력 불가)";
            case POWER: return "거듭제곱 (0^0과 0^음수는 불가)";
            case FACTORIAL: return "팩토리얼 (음수 입력 불가)";
            case SQUARE: return "제곱";
            default: return "";
        }
    }

}
