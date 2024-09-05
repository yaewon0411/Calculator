package util;

public class CustomDesign {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";


    public static void printWelcomeMessage() {
        System.out.println(ANSI_YELLOW);
        System.out.println("    _____      _            _       _             ");
        System.out.println("   / ____|    | |          | |     | |            ");
        System.out.println("  | |     __ _| | ___ _   _| | __ _| |_ ___  _ __ ");
        System.out.println("  | |    / _` | |/ __| | | | |/ _` | __/ _ \\| '__|");
        System.out.println("  | |___| (_| | | (__| |_| | | (_| | || (_) | |   ");
        System.out.println("   \\_____\\__,_|_|\\___|\\__,_|_|\\__,_|\\__\\___/|_|   ");
        System.out.println(ANSI_RESET);

        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "       지원하는 기본 산술 연산 목록" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println("            " + ANSI_YELLOW + "+ " + ANSI_RESET + "더하기");
        System.out.println("            " + ANSI_YELLOW + "- " + ANSI_RESET + "빼기");
        System.out.println("            " + ANSI_YELLOW + "* " + ANSI_RESET + "곱하기");
        System.out.println("            " + ANSI_YELLOW + "/ " + ANSI_RESET + "나누기");
        System.out.println(ANSI_CYAN + "--------------------------------------------" + ANSI_RESET);
        System.out.println("    " + ANSI_RED + "종료를 원하시면 'exit'을 입력해주세요" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "============================================" + ANSI_RESET);
        System.out.println();
    }
}
