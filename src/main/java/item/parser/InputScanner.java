package item.parser;

import java.util.Scanner;

public record InputScanner() {
    private static final Scanner sc = new Scanner(System.in);

    public String checkOrderValidation() {
        String target = sc.nextLine().toLowerCase();
        if (!target.matches("(o|q|order|quit)")) {
            throw new IllegalArgumentException("o, order, q, quit 만 입력 가능합니다.");
        }
        return target;
    }

    public String writeInfo() {
        return sc.nextLine();
    }
}
