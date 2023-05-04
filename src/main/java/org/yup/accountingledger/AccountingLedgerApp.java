package org.yup.accountingledger;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
public class AccountingLedgerApp {
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        readTransaction(transactions);

        while (true) {
            displayOptions();
            String option = input.nextLine().toUpperCase();
            switch (option) {
                case "D":
                    addDeposit(transactions);
                    break;
                case "P":
                    makePayment(transactions);
                    break;
                case "L":
                    viewLedger(transactions);
                    break;
                case "R":
                    runReports(transactions);
                    break;
                case "X":
                    input.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void displayOptions() {
        System.out.println("Choose an option:");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("R) Generate report") ;
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
    }
    private static void addDeposit(ArrayList<Transaction> transactions) {
        System.out.println("You chose to add a deposit.");
        System.out.println("Enter the deposit amount.");
        double depositAmount = Double.parseDouble(input.nextLine());
        System.out.print("Enter a description for the deposit");
        String depositDescription = input.nextLine();
        System.out.print("Enter a vendor");
        String vendor = input.nextLine();
        Transaction newTransaction = new Transaction(LocalDate.now(),LocalTime.now(), depositDescription, depositAmount, vendor);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);

    }
    private static void makePayment(ArrayList<Transaction> transactions) {
        System.out.println("You chose to make a payment.");
        System.out.println("Enter payment information: ");
        System.out.print("Payee Name: ");
        String payee = input.nextLine();
        System.out.print("Payment Amount: ");
        double amount = Double.parseDouble(input.nextLine());
        System.out.println("What vendor ");
        String vendor = input.nextLine();
        Transaction newTransaction = new Transaction(LocalDate.now(),LocalTime.now(), payee, -amount, vendor);
        transactions.add(newTransaction);
        saveTransaction(newTransaction);
        System.out.println("Payment added to transactions.csv");

    }
    private static void viewLedger(ArrayList<Transaction> transactions) {

        double balance = 0.0;
        System.out.println("Transaction History:");
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
            System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                    transaction.getVendor(),transaction.getType(), transaction.getAmount(), balance);
        }
        System.out.println("---------------------------------------------");
    }
    private static void runReports(ArrayList<Transaction> transactions) {
        while (true) {
            System.out.println("Choose a report to run:");
            System.out.println("1) Display deposits");
            System.out.println("2) Display payments");
            System.out.println("3) Month To Date");
            System.out.println("4) Previous Month");
            System.out.println("5) Year To Date");
            System.out.println("6) Previous Year");
            System.out.println("7) Search by Vendor");
            System.out.println("0) Back");
            String option = input.nextLine();
            switch (option) {
                case "1":
                    viewDeposits(transactions);
                    break;
                case "2":
                    viewPayments(transactions);
                    break;
                case "3":
                    viewReportByMonth(transactions, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
                    break;
                case "4":
                    viewReportByMonth(transactions, LocalDate.now().minusMonths(1).getMonthValue(), LocalDate.now().minusMonths(1).getYear());
                    break;
                case "5":
                    viewReportByYear(transactions, LocalDate.now().getYear());
                    break;
                case "6":
                    viewReportByYear(transactions, LocalDate.now().minusYears(1).getYear());
                    break;
                case "7":
                    System.out.print("Enter vendor name: ");
                    String vendorName = input.nextLine();
                    viewReportByVendor(transactions, vendorName);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    private static void viewReportByMonth(ArrayList<Transaction> transactions, int month, int year) {
        double balance = 0.0;
        System.out.printf("Transaction History for %s %d:%n",month, year);
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonthValue() == month && transaction.getDate().getYear() == year) {
                balance += transaction.getAmount();
                System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                        transaction.getVendor(), transaction.getType(), transaction.getAmount(), balance);
            }
        }
        System.out.println("---------------------------------------------");
    }
    private static void viewReportByVendor(ArrayList<Transaction> transactions, String vendorName) {
        double balance = 0.0;
        System.out.printf("Transaction History for Vendor: %s%n", vendorName);
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendorName)) {
                balance += transaction.getAmount();
                System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                        transaction.getVendor(), transaction.getType(), transaction.getAmount(), balance);
            }
        }
    }

    private static void viewReportByYear(ArrayList<Transaction> transactions, int year) {
        double balance = 0.0;
        System.out.printf("Transaction History for %d:%n", year);
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == year) {
                balance += transaction.getAmount();
                System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                        transaction.getVendor(), transaction.getType(), transaction.getAmount(), balance);
            }
        }
        System.out.println("---------------------------------------------");
    }
    private static void viewDeposits(ArrayList<Transaction> transactions) {
        double balance = 0.0;
        System.out.printf("Transaction History for :%n");
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount()>0) {
                balance += transaction.getAmount();
                System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                        transaction.getVendor(), transaction.getType(), transaction.getAmount(), balance);
            }
        }
        System.out.println("---------------------------------------------");
    }
    private static void viewPayments(ArrayList<Transaction> transactions) {
        double balance = 0.0;
        System.out.printf("Transaction History for :%n");
        System.out.println("---------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount()<0) {
                balance += transaction.getAmount();
                System.out.printf("%20s %s %s %s $%.2f Balance: $%.2f%n", transaction.getDate(), transaction.getTime(),
                        transaction.getVendor(), transaction.getType(), transaction.getAmount(), balance);
            }
        }
        System.out.println("---------------------------------------------");
    }


    private static void saveTransaction(Transaction transaction) {
        try {
            FileWriter fileWriter = new FileWriter("Transactions.csv", true);
            fileWriter.write(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getType() + "|" + transaction.getAmount() + "|" + transaction.getVendor() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static void readTransaction(ArrayList<Transaction> transactions){
        try (FileReader fileReader = new FileReader("Transactions.csv");
             Scanner fileScanner = new Scanner(fileReader)) {
            while (fileScanner.hasNextLine()) {
                String[] transactionDetails = fileScanner.nextLine().split("\\|");
                if (transactionDetails.length < 5) {
                    System.out.println("Warning: Skipping malformed transaction data.");
                    continue;
                }

                Transaction transaction = new Transaction(LocalDate.parse(transactionDetails[0]),LocalTime.parse(transactionDetails[1]), transactionDetails[2],
                        Double.parseDouble(transactionDetails[3]), transactionDetails[4]);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not find transactions file.");

        }

    }
}


