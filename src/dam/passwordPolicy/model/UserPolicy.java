package dam.passwordPolicy.model;

import java.util.function.Predicate;

public class UserPolicy extends PasswordPolicy{
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    private static final Predicate<String> FT_NN = (s) -> s != null;
    private static final Predicate<String> FT_MIN_L = (s) -> s.length() >= MIN_LENGTH;
    private static final Predicate<String> FT_MAX_L = (s) -> s.length() <= MAX_LENGTH;

    public UserPolicy() {
        super();
        addDefaultTests();
    }

    private void addDefaultTests() {
        addTest(FT_NN, "Password cannot be null");
        addTest(FT_MIN_L, "Password must be at least " + MIN_LENGTH + " characters");
        addTest(FT_MAX_L, "Password must be at most " + MAX_LENGTH + " characters");

        addContainsAtLeast("1234567890", "Password must contain at least 1 number");
        addContainsAtLeast(LOWER_LETTERS, "Password must contain at least 1 lowercase letter");
        addContainsAtLeast(UPPER_LETTERS, "Password must contain at least 1 uppercase letter");
    }
}
