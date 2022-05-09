package dam.passwordPolicy.model;

import java.util.function.Predicate;

public class BasicPasswordPolicy extends PasswordPolicy {
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
        tests.add(FT_NN);
        tests.add(FT_MIN_L);
        tests.add(FT_MAX_L);
        containsAtLeast.add(SPECIAL_CHARACTERS);
        containsAtLeast.add("1234567890");
        containsAtLeast.add("abcdefghijklmnopqrstuvwxyzáéíóúäëïöü");
        containsAtLeast.add("ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚÄËÏÖÜ");
    }
}
