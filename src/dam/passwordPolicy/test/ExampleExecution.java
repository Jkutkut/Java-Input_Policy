package dam.passwordPolicy.test;

import dam.passwordPolicy.model.BasicPasswordPolicy;

import java.util.Scanner;

/**
 * @author jkutkut
 */
public class ExampleExecution {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        askCredentials(sc);
    }

    private static void askCredentials(Scanner sc) {
        BasicPasswordPolicy policy = new BasicPasswordPolicy();
        String username, password;

        System.out.println("Enter a username: ");
        username = sc.nextLine();
        while (true) {
            System.out.println("Enter a password: ");
            password = sc.nextLine();
            try {
                policy.validate(password, username);
                break;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Exiting...");
    }
}
