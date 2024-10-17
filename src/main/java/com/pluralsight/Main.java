package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    displayLedger();
                    break;
                case 4:
                    runReports();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Financial Transaction Tracker!");
    }
    
    private static void displayMenu() {
        System.out.println("\n--- Financial Transaction Tracker ---");
        System.out.println("1. Add Deposit");
        System.out.println("2. Make Payment (Debit)");
        System.out.println("3. Ledger");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addDeposit() {
        System.out.print("Enter deposit date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter deposit time (HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        FileHandler.saveTransaction(deposit);
        System.out.println("Deposit added successfully.");
    }
    private static void makePayment() {
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter payment time (HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = -Double.parseDouble(scanner.nextLine());  // Negative for payments

        Transaction payment = new Transaction(date, time, description, vendor, amount);
        FileHandler.saveTransaction(payment);
        System.out.println("Payment added successfully.");
    }

    private static void displayLedger() {
        List<Transaction> transactions = FileHandler.readTransaction();
        System.out.println("\n--- Ledger ---");
        System.out.println("date|time|description|vendor|amount");
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }

    private static void runReports() {
        // Implement report generation logic here
        System.out.println("Report functionality not implemented yet.");
    }
}

