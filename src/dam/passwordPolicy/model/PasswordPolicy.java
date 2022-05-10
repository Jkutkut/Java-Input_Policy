package dam.passwordPolicy.model;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import dam.exception.InvalidDataException;

public class PasswordPolicy {

    private static final double SIMILARITY_THRESHOLD = 0.35;

    protected final ArrayList<Predicate<String>> tests;
    protected final ArrayList<String> testsMsgs;
    protected final ArrayList<String> distinctStringsMsgs;
    protected final ArrayList<String> distinctStrings;
    protected final ArrayList<String> containsAtLeast;
    protected final ArrayList<String> containsAtLeastMsgs;

    public PasswordPolicy() {
        tests = new ArrayList<Predicate<String>>();
        distinctStrings = new ArrayList<String>();
        containsAtLeast = new ArrayList<String>();

        testsMsgs = new ArrayList<String>();
        distinctStringsMsgs = new ArrayList<String>();
        containsAtLeastMsgs = new ArrayList<String>();
    }

    // GETTERS

    public boolean isValid(String password) {
        int i;
        for (i = 0; i < tests.size(); i++)
            if (!tests.get(i).test(password))
                return false;

        for (i = 0; i < distinctStrings.size(); i++)
            if (stringsAlike(password, distinctStrings.get(i)))
                return false;

        for (i = 0; i < containsAtLeast.size(); i++)
            if (!password.matches(String.format(".*[%s].*", containsAtLeast.get(i))))
                return false;

        String all = "";
        for (i = 0; i < containsAtLeast.size(); i++)
            all += containsAtLeast.get(i);
        if (!password.matches(String.format("[%s]*", all)))
            return false;
        return true;
    }

    public void validate(String password) throws InvalidDataException {
        int i;
        for (i = 0; i < tests.size(); i++)
            if (!tests.get(i).test(password))
                throw new InvalidDataException(testsMsgs.get(i));

        for (i = 0; i < distinctStrings.size(); i++)
            if (stringsAlike(password, distinctStrings.get(i)))
                throw new InvalidDataException(distinctStringsMsgs.get(i));

        for (i = 0; i < containsAtLeast.size(); i++)
            if (!password.matches(String.format(".*[%s].*", containsAtLeast.get(i))))
                throw new InvalidDataException(containsAtLeastMsgs.get(i));
        String all = "";
        for (i = 0; i < containsAtLeast.size(); i++)
            all += containsAtLeast.get(i);
        if (!password.matches(String.format("[%s]*", all)))
            throw new InvalidDataException("Password can only contain the following chars: " + all);
    }

    // SETTERS

    public void addTest(Predicate<String> test, String msg) {
        if (test == null)
            throw new InvalidDataException("Test cannot be null");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (tests.contains(test))
            return;
        tests.add(test);
        testsMsgs.add(msg);
    }

    public void addDistinctString(String string, String msg) {
        if (string == null)
            throw new InvalidDataException("String cannot be null. Use the tests to check this.");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (distinctStrings.contains(string))
            return;
        distinctStrings.add(string);
        distinctStringsMsgs.add(msg);
    }

    public void addContainsAtLeast(String string, String msg) {
        if (string == null)
            throw new InvalidDataException("String cannot be null. Use the tests to check this.");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (containsAtLeast.contains(string))
            return;
        containsAtLeast.add(string);
        containsAtLeastMsgs.add(msg);
    }

    // TOOLS

    protected static boolean stringsAlike(String s1, String s2) {
        return similarity(s1, s2) > SIMILARITY_THRESHOLD;
    }

    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0)
            return 1.0; // Equal legth
        return (longerLength - levenshteinDist(longer, shorter)) / (double) longerLength;
    }

    /**
     * http://rosettacode.org/wiki/Levenshtein_distance#Java
     */
    private static int levenshteinDist(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0, j, lastValue, newValue; i <= s1.length(); i++) {
            lastValue = i;
            for (j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
