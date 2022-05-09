package dam.passwordPolicy.test;

import dam.passwordPolicy.PasswordPolicy;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class TestPasswordPolicy {
    PasswordPolicy passwordPolicy;
    @BeforeClass
    public void setUpClass() {
        passwordPolicy = new PasswordPolicy();
    }

    @Test
    public void testPasswordPolicy() {
        String[] passwords = {
            "123456",
            "123456789",
            "12345678",
            "",
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
                assertEquals(passwordPolicy.isValid(password), false);
            }
        }
    }
}
