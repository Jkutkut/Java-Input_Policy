package jkutkut.inputPolicy.model;

import java.util.function.Predicate;

/**
 * Policy to validate a user.
 *
 * @author jkutkut
 */
public class UserPolicy extends InputPolicy {
    protected static final int MIN_LENGTH = 3;
    protected static final int MAX_LENGTH = 20;

    protected static final String POLICY_NAME = "User";
    protected static final String MIN_L_MSG = "%s must be at least %d characters long";
    protected static final String MAX_L_MSG = "%s must be at most %d characters long";

    protected static final Predicate<String> FT_NN = (s) -> s != null;
    protected static final Predicate<String> FT_MIN_L = (s) -> s.length() >= MIN_LENGTH;
    protected static final Predicate<String> FT_MAX_L = (s) -> s.length() <= MAX_LENGTH;

    public UserPolicy() {
        super();
        addTests();
    }

    protected void addTests() {
        addTest(FT_NN, "User cannot be null");
        addTest(FT_MIN_L, String.format(MIN_L_MSG, getPolicyName(), getMinLength()));
        addTest(FT_MAX_L, String.format(MAX_L_MSG, getPolicyName(), getMaxLength()));
    }

    // ****** GETTERS ******

    protected String getPolicyName() {
        return POLICY_NAME;
    }

    protected int getMinLength() {
        return MIN_LENGTH;
    }

    protected int getMaxLength() {
        return MAX_LENGTH;
    }
}

