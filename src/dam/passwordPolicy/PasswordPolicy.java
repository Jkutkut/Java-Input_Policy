package dam.passwordPolicy;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PasswordPolicy {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private static final String SPECIAL_CHARACTERS = "&+_*";
    private static final String REGEX_ALL_CHARACTERS = "[a-zA-Z0-9" + SPECIAL_CHARACTERS + "]*";

    private static final Predicate<String> FT_NN = (s) -> s != null;
    private static final Predicate<String> FT_MIN_L = (s) -> s.length() >= MIN_LENGTH;
    private static final Predicate<String> FT_MAX_L = (s) -> s.length() <= MAX_LENGTH;

    private static final Predicate<String> FT_1DIGIT = (s) -> s.matches(".*[0-9].*");
    private static final Predicate<String> FT_1LOWER = (s) -> s.matches(".*[a-z].*");
    private static final Predicate<String> FT_1UPPER = (s) -> s.matches(".*[A-Z].*");
    private static final Predicate<String> FT_AbC = (s) -> FT_1LOWER.test(s) && FT_1UPPER.test(s);

    private static final Predicate<String>[] CHECKERS = new Predicate[] {
        FT_NN,
        FT_MIN_L,
        FT_MAX_L,

        FT_1DIGIT,
        FT_1LOWER,
        FT_1UPPER,
        FT_AbC
    };

    private ArrayList<Integer> tests;
    private ArrayList<String> distinctStrings;

    public PasswordPolicy() {
        tests = new ArrayList<Integer>();
        distinctStrings = new ArrayList<String>();

        setDefaultTests();
    }

    private void setDefaultTests() {
        for (int i = 0; i < CHECKERS.length; i++) {
            tests.add(i);
        }
    }

    public void addDistinctStringTest(String s) {
        if (!distinctStrings.contains(s)) {
            distinctStrings.add(s);
        }
    }

    public boolean isValid(String password) {
        for (Integer i : tests) {
            if (!CHECKERS[i].test(password))
                return false;
        }

        // TODO check for distinct strings
        return true;
    }


    public static void main(String[] args) {
        int min = 10;
        Predicate<String> p = s -> s.length() >= min;
        System.out.println(p.test("123456789"));
        System.out.println(p.test("123456789 123456789"));
        System.out.println(p.test("123"));
    }
}
