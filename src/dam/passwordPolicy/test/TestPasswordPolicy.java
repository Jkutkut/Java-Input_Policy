package dam.passwordPolicy.test;

import dam.passwordPolicy.model.PasswordPolicy;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.*;

public class TestPasswordPolicy {
    PasswordPolicy passwordPolicy;
    @BeforeClass
    public void setUpClass() {
        passwordPolicy = new PasswordPolicy();
    }

    @Test
    public void testInvalidFormat() {
        String[] passwords = {
            null,
            "",
            " ",
            "\0"
        };
        for (String password : passwords) {
            if (passwordPolicy.isValid(password)) {
                System.out.println(password + " is valid");
                assertFalse(passwordPolicy.isValid(password));
            }
        }
    }

    @Test
    public void testMinLength() {
        String[] passwords = {
            "aC#12",
            "C1a$2"
        };
        assertFalse(passwordPolicy.isValid(passwords[0]));
        assertFalse(passwordPolicy.isValid(passwords[1]));
    }

    @Test
    public void testMaxLength() {
        String[] passwords = {
                "aC#12jfklasdjflkasjdklfjasldfjaklsdjflasdjflasdjlfajsdklfjklsdjflksadjfkljasdl",
                "C1jfklsdjafklsajlfjsdklfjsdklajfklsdajlfdsfljsdaklfjsdklfjlkasdfsdjklkjla$2"
        };
        assertFalse(passwordPolicy.isValid(passwords[0]));
        assertFalse(passwordPolicy.isValid(passwords[1]));
    }

    @Test
    public void testPasswordPolicy() {
        String[] passwords = {
            "123456",
            "123456789",
            "12345678",
            "a",
            "abc",
            "abc123",
            "abc123456789",
            "123456#",
            "123456$"
        };
        for (String password : passwords) {
            if (passwordPolicy.isValid(password)) {
                System.out.println(password + " is valid");
                assertEquals(false, passwordPolicy.isValid(password));
            }
        }
    }

    @Test
    public void testValid() {
        String[] passwords = {
            "holaQue42$"
        };
        for (String password : passwords) {
            if (!passwordPolicy.isValid(password)) {
                System.out.println(password + " is invalid");
                assertEquals(true, passwordPolicy.isValid(password));
            }
        }
    }
}
