package jkutkut.passwordPolicy.model;

import java.util.function.Predicate;

/**
 * Basic password policy with some rules.
 *
 * @author jkutkut
 */
public class BasicPasswordPolicy extends InputPolicy {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private static final String SPECIAL_CHARACTERS = "&+_*";

    private static final Predicate<String> FT_NN = (s) -> s != null;
    private static final Predicate<String> FT_MIN_L = (s) -> s.length() >= MIN_LENGTH;
    private static final Predicate<String> FT_MAX_L = (s) -> s.length() <= MAX_LENGTH;

    public BasicPasswordPolicy() {
        super();
        addDefaultTests();
    }

    private void addDefaultTests() {
        addTest(FT_NN, "Password cannot be null");
        addTest(FT_MIN_L, "Password must be at least " + MIN_LENGTH + " characters");
        addTest(FT_MAX_L, "Password must be at most " + MAX_LENGTH + " characters");

        addContainsAtLeast(SPECIAL_CHARACTERS, "Password must contain at least 1 special character (" + SPECIAL_CHARACTERS + ")");
        addContainsAtLeast(NUMBERS, "Password must contain at least 1 number");
        addContainsAtLeast(LOWER_LETTERS, "Password must contain at least 1 lowercase letter");
        addContainsAtLeast(UPPER_LETTERS, "Password must contain at least 1 uppercase letter");

        String errorDis = "Really? Password in 2022 with this type of logic?";
        addDistinctString("1234", errorDis);
        addDistinctString("password", errorDis);
        addDistinctString("constaseña", errorDis);
        addDistinctString("hola", errorDis);
        addDistinctString("user", errorDis);
        addDistinctString("root", errorDis);
        addDistinctString("admin", errorDis);
    }
}
