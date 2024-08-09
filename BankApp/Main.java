import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private HashMap<String, String> userMap = new HashMap<>();
    private HashMap<String, Double> balanceMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    
    public Main() {
        loadUserCredentials();
        loadBalances();
    }

    // Load user credentials from file
    private void loadUserCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user credentials file.");
        }
    }

    // Load balances from file
    private void loadBalances() {
        try (BufferedReader reader = new BufferedReader(new FileReader("balances.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    balanceMap.put(parts[0], Double.parseDouble(parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading balances file.");
        }
    }

    // Save user credentials to file
    private void saveUserCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (HashMap.Entry<String, String> entry : userMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing user credentials file.");
        }
    }

    // Save balances to file
    private void saveBalances() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("balances.txt"))) {
            for (HashMap.Entry<String, Double> entry : balanceMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing balances file.");
        }
    }

    public void Login() {
        System.out.println("Login with your registered username and password");
        System.out.print("Enter registered username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
            System.out.println("User Logged in successfully! 😀");
        } else {
            System.out.println("Login failed. Login with correct details 🙍");
        }
    }

    public void Register() {
        System.out.println("Register a new TechBank Account:");
        System.out.print("Enter a Username: ");
        String username = scanner.nextLine();

        if (userMap.containsKey(username)) {
            System.out.println("Username already exists. Choose another name 🙍");
            return;
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        userMap.put(username, password);
        balanceMap.put(username, 0.0); // Initial balance
        saveUserCredentials();
        saveBalances();
        System.out.println("User registered successfully 😀");
    }

    public void Deposit(String username, Double amount) {
        System.out.println("Deposit with us");
        if (balanceMap.containsKey(username)) {
            double currentBalance = balanceMap.get(username);
            balanceMap.put(username, currentBalance + amount);
            saveBalances();
            System.out.println("Deposit made successfully 😀\nNew Balance is " + balanceMap.get(username));
        } else {
            System.out.println("Username not Found");
        }
    }

    public void Withdraw(String username, double amount) {
        if (balanceMap.containsKey(username)) {
            double currentBalance = balanceMap.get(username);
            if (amount <= currentBalance) {
                balanceMap.put(username, currentBalance - amount);
                saveBalances();
                System.out.println("Withdrawal successful\nNew Balance " + balanceMap.get(username));
            } else {
                System.out.println("Insufficient Funds");
            }
        } else {
            System.out.println("User not Found");
        }
    }

    public void CheckBalance(String username) {
        if (balanceMap.containsKey(username)) {
            double balance = balanceMap.get(username);
            System.out.println("Your bank Balance is " + balance);
        } else {
            System.out.println("User not Found");
        }
    }

    public void Exit() {
        System.out.println("Exiting the application...");
        scanner.close();
    }

    public static void main(String[] args) {
        Main BankApp = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                "Welcome to TechBank\n1. Login\n2. Check Balance\n3. Withdraw\n4. Deposit\n5. Exit\n6. Not Registered? (Register)"
            );
            System.out.print("Enter operation from above choices: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    BankApp.Login();
                    break;
                case "2":
                    System.out.print("Enter username to check balance: ");
                    String username2 = scanner.nextLine();
                    BankApp.CheckBalance(username2);
                    break;
                case "3":
                    System.out.print("Enter username to withdraw: ");
                    String username3 = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double amountToWithdraw = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    BankApp.Withdraw(username3, amountToWithdraw);
                    break;
                case "4":
                    System.out.print("Enter username to deposit: ");
                    String username4 = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double amountToDeposit = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    BankApp.Deposit(username4, amountToDeposit);
                    break;
                case "5":
                    BankApp.Exit();
                    return; // Exit the main method
                case "6":
                    BankApp.Register();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
