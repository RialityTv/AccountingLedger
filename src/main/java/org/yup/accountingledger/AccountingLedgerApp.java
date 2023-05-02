
package org.yup.accountingledger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {

    public static void main(String[] args) {
        try {

//    This code creates a Scanner object to get user input and sets the running variable to true to keep the application running until the user chooses to exit.
//
//        The options are displayed to the user using System.out.println() and the user's choice is obtained using Scanner.nextLine().
//
//        The user's choice is then compared to the available options using a switch statement, and the appropriate logic is executed based on the user's choice.
//
//        Finally, the input object is closed before the application exits.


            Scanner input = new Scanner(System.in);
            boolean running = true;

            ArrayList<Transaction> transactions = new ArrayList<>();



            while (running) {
                System.out.println("Choose an option:");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment (Debit)");
                System.out.println("L) Ledger");
                System.out.println("X) Exit");

                String option = input.nextLine();

                switch (option.toUpperCase()) {
//                this code will prompt the user for a deposit amount and description
//                then append a new line to the end of the "transactions.csv" file with the deposit information in CSV format.
//                the FileWriter is opened in "append" mode, which means that new data will be added to the end of the file rather than overwriting any existing data.
//                The flush() method ensures that any buffered data is written to the file
//                the close() method releases any system resources associated with the file.
                    case "D":
                        // Add deposit logic here
                        System.out.println("You chose to add a deposit.");
                        System.out.println("Enter the deposit amount.");
                        double depositAmount = Double.parseDouble(input.nextLine());


                        System.out.print("Enter a description for the deposit");
                        String depositDescription = input.nextLine();

                        System.out.print("Enter a vendor");
                        String vendor = input.nextLine();

                        Transaction newtransaction = new Transaction(LocalDate.now(), depositDescription, depositAmount, vendor);
//                        transactions.add(newTransaction);
                        WriteTransaction(newtransaction);

                        break;
                    case "P":
                        // Make payment logic here
                        System.out.println("You chose to make a payment.");
                        System.out.println("Enter payment information: ");
                        System.out.print("Payee Name: ");
                        String payee = input.nextLine();
                        System.out.print("Payment Amount: ");
                        double amount = input.nextDouble();
                        System.out.println("What vendor");
                        String Vendor2 = input.nextLine();
                        input.nextLine();

                        Transaction newTransaction2 = new Transaction(LocalDate.now(),payee, amount, Vendor2 );

                        WriteTransaction(newTransaction2);
                        System.out.println("Payment added to transactions.csv");


                        break;
                    case "L":
                        // Display ledger logic here
                        System.out.println("You chose to view the ledger.");
                        FileReader fw = new FileReader("Transactions.csv");
                        Scanner fileScanner = new Scanner(fw);

                        while (fileScanner.hasNextLine()) {
                            String[] transactionDetails = fileScanner.nextLine().split("\\|");
                            Transaction transaction = new Transaction(LocalDate.now(), transactionDetails[2],
                                    Double.parseDouble(transactionDetails[3]),
                                    transactionDetails[4]);
                            transactions.add(transaction);

                        }
                        fileScanner.close();

                        // Display transactions and balance
                        double balance = 0.0;
                        System.out.println("Transaction History:");
                        System.out.println("---------------------------------------------");
                        for (Transaction transaction : transactions) {
                            if (transaction.getType().equals("Deposit")) {
                                balance += transaction.getAmount();
                            } else {
                                balance -= transaction.getAmount();
                            }
                            System.out.printf("%-20s %-10s $%.2f Balance: $%.2f%n", transaction.getDate(),
                                    transaction.getType(), transaction.getAmount(), balance);
//                    }
//                    System.out.println("---------------------------------------------");

//        }catch (IOException e) {
//                System.out.println("Error: Could not find transactions file.");
//             e.printStackTrace();
//            }

                        }
                        break;
                    case "X":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again :0.");


//            input.close();
                }


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void WriteTransaction(Transaction transaction) throws IOException {

        FileWriter writer = new FileWriter("Transactions.csv", true);
        writer.append(transaction.getDate() + "|" + transaction.getAmount() + "|" + transaction.getVendor() + "|" + transaction.getType());
        writer.flush();
        writer.close();
        System.out.println("Saved To ledger.");
    }
}






//    This code creates a Scanner object to get user input and sets the running variable to true to keep the application running until the user chooses to exit.
//
//        The options are displayed to the user using System.out.println() and the user's choice is obtained using Scanner.nextLine().
//
//        The user's choice is then compared to the available options using a switch statement, and the appropriate logic is executed based on the user's choice.
//
//        Finally, the input object is closed before the application exits.