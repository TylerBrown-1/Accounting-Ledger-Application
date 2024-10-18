package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {

        // cont. loop to display menu
        // used int and numbers for easier user input
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Financial Transaction Tracker!");
    }

    // to display menu
    private static void displayMenu() {
        System.out.println("\nWelcome to the Financial Transaction Tracker ");
        System.out.println("1. Add Deposit");
        System.out.println("2. Make Payment (Debit)");
        System.out.println("3. Ledger");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // to get user's menu choice
    private static int getUserChoice() {

        return Integer.parseInt(reader.nextLine());
    }

    //method to add a deposit trans; prompts user for details
    private static void addDeposit() {
        System.out.print("Enter deposit date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(reader.nextLine());
        System.out.print("Enter deposit time (HH:mm:ss): ");
        LocalTime time = LocalTime.parse(reader.nextLine());
        System.out.print("Enter description: ");
        String description = reader.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = reader.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(reader.nextLine());

    //create and save the deposit trans
        Transaction deposit = new Transaction(date, time, description, vendor, amount);
        FileHandler.saveTransaction(deposit);
        System.out.println("Deposit added successfully.");
    }

    //method to add a payment
    private static void makePayment() {
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(reader.nextLine());
        System.out.print("Enter payment time (HH:mm:ss):");
        LocalTime time = LocalTime.parse(reader.nextLine());
        System.out.print("Enter description: ");
        String description = reader.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = reader.nextLine();
        System.out.print("Enter amount: ");
        double amount = -Double.parseDouble(reader.nextLine());
        Transaction payment = new Transaction(date, time, description, vendor, amount);
        FileHandler.saveTransaction(payment);
        System.out.println("Payment added successfully.");
    }

    //method to display all trans in ledger; reads from file
    private static void displayLedger() {
        boolean ledgerRunning = true;
        while (ledgerRunning) {
            displayLedgerMenu();
            int choice = getUserChoice();
            List<Transaction> transactions = FileHandler.readTransaction();

            switch (choice) {
                case 1:
                    displayAllEntries(transactions);
                    break;
                case 2:
                    displayDeposits(transactions);
                    break;
                case 3:
                    displayPayments(transactions);
                    break;
                case 4:
                    runReports();
                    break;
                case 0:
                    ledgerRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //Displays the ledger menu options to the user.

    private static void displayLedgerMenu() {
        System.out.println("\n--- Ledger Menu ---");
        System.out.println("1. All Entries");
        System.out.println("2. Deposits");
        System.out.println("3. Payments");
        System.out.println("4. Reports");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    //Displays all transactions in the ledger

    private static void displayAllEntries(List<Transaction> transactions) {
        System.out.println("\n--- All Entries ---");
        displayTransactions(transactions);
    }

    // displays only deposit transactions.
    private static void displayDeposits(List<Transaction> transactions) {
        System.out.println("\n--- Deposits ---");
        List<Transaction> deposits = transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .collect(Collectors.toList());
        displayTransactions(deposits);
    }

    //Displays only payment transactions.

    private static void displayPayments(List<Transaction> transactions) {
        System.out.println("\n--- Payments ---");
        List<Transaction> payments = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.toList());
        displayTransactions(payments);
    }

    //method to display a list of transactions.

    private static void displayTransactions(List<Transaction> transactions) {
        System.out.println("Date | Time | Description | Vendor | Amount");
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }

    //Manages the report generation process.

    private static void runReports() {
        boolean reportRunning = true;
        while (reportRunning) {
            displayReportMenu();
            int choice = getUserChoice();
            List<Transaction> transactions = FileHandler.readTransaction();

            switch (choice) {
                case 1:
                    displayFilteredTransactions(getMonthToDateTransactions(transactions));
                    break;
                case 2:
                    displayFilteredTransactions(getPreviousMonthTransactions(transactions));
                    break;
                case 3:
                    displayFilteredTransactions(getYearToDateTransactions(transactions));
                    break;
                case 4:
                    displayFilteredTransactions(getPreviousYearTransactions(transactions));
                    break;
                case 5:
                    searchByVendor(transactions);
                    break;
                case 0:
                    reportRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



        //  display the report menu
        private static void displayReportMenu() {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Month To Date");
            System.out.println("2. Previous Month");
            System.out.println("3. Year To Date");
            System.out.println("4. Previous Year");
            System.out.println("5. Search by Vendor");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }

        // display filtered transactions
        private static void displayFilteredTransactions(List<Transaction> transactions) {
            System.out.println("\n--- Filtered Transactions ---");
            System.out.println("date|time|description|vendor|amount");
            for (Transaction t : transactions) {
                System.out.println(t.toString());
            }
        }

        //  filters by current month to current date
        private static List<Transaction> getMonthToDateTransactions(List<Transaction> transactions) {
            LocalDate now = LocalDate.now();
            LocalDate startOfMonth = now.withDayOfMonth(1);
            return transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startOfMonth) && !t.getDate().isAfter(now))
                    .collect(Collectors.toList());
        }
        // filters by prev. month
        private static List<Transaction> getPreviousMonthTransactions(List<Transaction> transactions) {
            LocalDate now = LocalDate.now();
            LocalDate startOfPreviousMonth = now.minusMonths(1).withDayOfMonth(1);
            LocalDate endOfPreviousMonth = startOfPreviousMonth.plusMonths(1).minusDays(1);
            return transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startOfPreviousMonth) && !t.getDate().isAfter(endOfPreviousMonth))
                    .collect(Collectors.toList());
        }
        //filters by year to date
        private static List<Transaction> getYearToDateTransactions(List<Transaction> transactions) {
            LocalDate now = LocalDate.now();
            LocalDate startOfYear = now.withDayOfYear(1);
            return transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startOfYear) && !t.getDate().isAfter(now))
                    .collect(Collectors.toList());
        }
        // filters by previous year
        private static List<Transaction> getPreviousYearTransactions(List<Transaction> transactions) {
            LocalDate now = LocalDate.now();
            LocalDate startOfPreviousYear = now.minusYears(1).withDayOfYear(1);
            LocalDate endOfPreviousYear = startOfPreviousYear.plusYears(1).minusDays(1);
            return transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startOfPreviousYear) && !t.getDate().isAfter(endOfPreviousYear))
                    .collect(Collectors.toList());
        }

        // method to search transactions by vendor name
        private static void searchByVendor(List<Transaction> transactions) {
            System.out.print("Enter vendor name: ");
            String vendorName = reader.nextLine();
            List<Transaction> filteredTransactions = transactions.stream()
                    .filter(t -> t.getVendor().equalsIgnoreCase(vendorName))
                    .collect(Collectors.toList());
            displayFilteredTransactions(filteredTransactions);
        }
    }




