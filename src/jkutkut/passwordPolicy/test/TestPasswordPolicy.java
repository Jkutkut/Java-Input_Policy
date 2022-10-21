package jkutkut.passwordPolicy.test;

import jkutkut.passwordPolicy.model.BasicPasswordPolicy;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.*;

/**
 * @author jkutkut
 */
public class TestPasswordPolicy {
    BasicPasswordPolicy passwordPolicy;
    @BeforeClass
    public void setUpClass() {
        passwordPolicy = new BasicPasswordPolicy();
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
    public void testSpecialChars() {
        String[] passwords = {
            "aStClp&42 ",
            "jfkds fdS4#",
            " jfkdsfdS4$"
        };
        for (String password : passwords) {
            if (passwordPolicy.isValid(password)) {
                System.out.println(password + " is valid");
                assertEquals(false, passwordPolicy.isValid(password));
            }
        }
    }

    @Test
    public void testSimilar() {
        String[] passwords = {
                "hola1234A&&"
        };
        for (String password : passwords) {
            if (passwordPolicy.isValid(password)) {
                System.out.println(password + " is valid");
                assertEquals(false, passwordPolicy.isValid(password));

            }
        }
    }

    @Test
    public void testCommon() {
        String[] passwords = {
            "admin&42",
            "fdSroot4#",
            "dS4user$"
        };
        for (int i = 0; i < passwords.length; i++) {
            if (passwordPolicy.isValid(passwords[i])) {
                System.out.println(passwords[i] + " is valid");
                assertEquals(false, passwordPolicy.isValid(passwords[i]));
            }
        }
    }

    @Test
    public void testSameUser() {
        String[] users = {
                "xXluisGomezXx",
                "6969LuisitoComunica",
                "LuisoteElMaxote"
        };
        String[] passwords = {
                "pLuisGomez4&",
                "comunicaLuisito36969&",
                "&LuisitoMaxote"
        };
        for (int i = 0; i < users.length; i++) {
            if (passwordPolicy.isValid(passwords[i], users[i])) {
                System.out.println(passwords[i] + " is valid");
                assertEquals(false, passwordPolicy.isValid(passwords[i], users[i]));
            }
        }
    }

    @Test
    public void testValid() {
        String[] passwords = {
            "aStClp&42"
        };
        for (String password : passwords) {
            if (!passwordPolicy.isValid(password)) {
                System.out.println(password + " is invalid");
                passwordPolicy.validate(password);
                assertEquals(true, passwordPolicy.isValid(password));
            }
        }
    }
}
