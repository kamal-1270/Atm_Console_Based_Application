import java.util.*;
public class Main {
    private static final Map<String, User> Users = new HashMap<>();
    private static User currentUser;

    public static void main(String[] args) {
        //check user
        AvailableUser();
        if (authenticateUser()) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
            menu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }
        //Enter New User
    private static void AvailableUser() {
        Users.put("kamal1270", new User("kamal1270", "1270", 10000.0));
        Users.put("rahul123", new User("rahul123", "1234", 500.0));
        Users.put("alok1246", new User("alok1246", "1246", 5000.0));
        Users.put("vicky1261", new User("vicky1261", "1261", 1500.0));
    }
        //check user
    private static boolean authenticateUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = sc.next();
        System.out.print("Enter PIN: ");
        String pin = sc.next();
        if (Users.containsKey(userId) && Users.get(userId).getPin().equals(pin)) {
            currentUser = Users.get(userId);
            return true;
        }
        return false;
    }
        // Atm menu
    private static void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Withdraw\n2. Deposit\n3. Transfer\n4. Check Balance\n5. Quit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: // Withdraw
                    System.out.print("Enter amount to withdraw: ");
                    Transaction(sc.nextDouble(), true);
                    break;
                case 2: // Deposit
                    System.out.print("Enter amount to deposit: ");
                    Transaction(sc.nextDouble(), false);
                    break;
                case 3: // Transfer
                    System.out.print("Enter recipient's User ID: ");
                    String recipientUserId = sc.next();
                    System.out.print("Enter amount to Transfer: ");
                    double amount = sc.nextDouble();
                    Transfer(recipientUserId, amount);
                    break;
                case 4: // Check Balance
                    System.out.println("Current balance: " + currentUser.getBalance());
                    break;
                case 5: // Quit
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
        //Transaction
    private static void Transaction(double amount, boolean isWithdrawal) {
        if (amount > 0 && (isWithdrawal ? amount <= currentUser.getBalance() : true)) {
            currentUser.setBalance(currentUser.getBalance() + (isWithdrawal ? -amount : amount));
            System.out.println((isWithdrawal ? "Withdrawal" : "Deposit") + " successful. New balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }
        //Transfer
    private static void Transfer(String recipientUserId, double amount) {
        if (amount > 0 && Users.containsKey(recipientUserId) && !recipientUserId.equals(currentUser.getUserId())) {
            User recipient = Users.get(recipientUserId);
            if (amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);
                System.out.println("Transfer successful. Remaining balance: " + currentUser.getBalance());
            } else {
                System.out.println("Insufficient balance for Transfer.");
            }
        } else {
            System.out.println("Invalid recipient User ID or Transfer amount.");
        }
    }

    private static class User {
        private final String userId;
        private final String pin;
        private double balance;

        public User(String userId, String pin, double balance) {
            this.userId = userId;
            this.pin = pin;
            this.balance = balance;
        }

        public String getUserId() {
             return userId;
             }
        public String getPin() {
             return pin;
             }
        public double getBalance() {
             return balance;
             }
        public void setBalance(double balance) {
             this.balance = balance;
             }
    }
}
