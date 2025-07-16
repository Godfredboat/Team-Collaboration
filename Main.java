
    import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
    
    public class Main {
        static Set<String> categories = new HashSet<>();
        static Map<String, BankAccount> accounts = new HashMap<>();
        static Map<String, Expenditure> expenditures = new HashMap<>();
        static Queue<String> receiptQueue = new LinkedList<>();
        static Stack<String> reviewStack = new Stack<>();
    
        public static void main(String[] args) {
            loadCategories();
            loadAccounts();
            loadExpenditures();
    
            Scanner sc = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\n---- Expenditure Management CLI ----");
                System.out.println("1. Add Expenditure");
                System.out.println("2. Add Category");
                System.out.println("3. Add Bank Account");
                System.out.println("4. View Banks");
                System.out.println("5. Add Receipt to Queue");
                System.out.println("6. Process Receipts");
                System.out.println("7. Search by Category");
                System.out.println("8. Exit");
                System.out.print("Choice: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Enter a valid integer choice:");
                    sc.next();
                }
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (choice) {
                    case 1: addExpenditure(sc); break;
                    case 2: addCategory(sc); break;
                    case 3: addBankAccount(sc); break;
                    case 4: viewBanks(); break;
                    case 5: addReceipt(sc); break;
                    case 6: processReceipts(); break;
                    case 7: searchByCategory(sc); break;
                    case 8: break;
                    default: System.out.println("Invalid choice.");
                }
            } while (choice != 8);
    
            saveCategories();
            saveAccounts();
            saveExpenditures();
            System.out.println("Data saved. Goodbye!");
            sc.close();
        }
    
        // ===================== DATA STRUCTURES =====================
        static class Expenditure {
            String code;
            double amount;
            String date;
            String phase;
            String category;
            String accountID;
        }
    
        static class BankAccount {
            String accountID;
            String bankName;
            double balance;
            List<String> expenditureCodes = new ArrayList<>();
        }
    
        // ===================== ADD FUNCTIONS ======================
        static void addCategory(Scanner sc) {
            System.out.print("Enter new category: ");
            String cat = sc.nextLine().trim();
            if (cat.isEmpty()) {
                System.out.println("Category cannot be empty.");
                return;
            }
            if (categories.add(cat)) {
                System.out.println("Category added.");
            } else {
                System.out.println("Category already exists.");
            }
        }
    
        static void addBankAccount(Scanner sc) {
            BankAccount acc = new BankAccount();
            System.out.print("Enter Account ID: ");
            acc.accountID = sc.nextLine().trim();
            System.out.print("Enter Bank Name: ");
            acc.bankName = sc.nextLine().trim();
            System.out.print("Enter Balance: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Enter a valid number:");
                sc.next();
            }
            acc.balance = sc.nextDouble();
            sc.nextLine();
            accounts.put(acc.accountID, acc);
            System.out.println("Bank account added.");
        }
    
        static void addExpenditure(Scanner sc) {
            Expenditure exp = new Expenditure();
            System.out.print("Enter Expenditure Code: ");
            exp.code = sc.nextLine().trim();
            System.out.print("Amount: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Enter a valid amount:");
                sc.next();
            }
            exp.amount = sc.nextDouble();
            sc.nextLine();
            System.out.print("Date (YYYY-MM-DD): ");
            exp.date = sc.nextLine().trim();
            System.out.print("Phase: ");
            exp.phase = sc.nextLine().trim();
            System.out.print("Category: ");
            exp.category = sc.nextLine().trim();
            System.out.print("Account ID: ");
            exp.accountID = sc.nextLine().trim();
    
            if (!categories.contains(exp.category)) {
                System.out.println("Category not found. Please add category first.");
                return;
            }
            if (!accounts.containsKey(exp.accountID)) {
                System.out.println("Account ID does not exist.");
                return;
            }
    
            expenditures.put(exp.code, exp);
            accounts.get(exp.accountID).expenditureCodes.add(exp.code);
            accounts.get(exp.accountID).balance -= exp.amount;
    
            System.out.println("Expenditure recorded and balance updated.");
        }
    
        // ===================== RECEIPTS ===========================
        static void addReceipt(Scanner sc) {
            System.out.print("Enter receipt filename: ");
            String rec = sc.nextLine().trim();
            receiptQueue.add(rec);
            System.out.println("Added to upload queue.");
        }
    
        static void processReceipts() {
            if (receiptQueue.isEmpty()) {
                System.out.println("No receipts to process.");
                return;
            }
            String rec = receiptQueue.poll();
            reviewStack.push(rec);
            System.out.println("Processed " + rec + " and moved to review stack.");
        }
    
        // ===================== SEARCH & VIEW ======================
        static void searchByCategory(Scanner sc) {
            System.out.print("Enter category to search: ");
            String cat = sc.nextLine().trim();
            boolean found = false;
            for (Expenditure exp : expenditures.values()) {
                if (exp.category.equalsIgnoreCase(cat)) {
                    System.out.println("Found: " + exp.code + " Amount: " + exp.amount
                            + " Date: " + exp.date + " Account: " + exp.accountID);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No expenditures found in this category.");
            }
        }
    
        static void viewBanks() {
            if (accounts.isEmpty()) {
                System.out.println("No bank accounts recorded.");
            } else {
                for (BankAccount acc : accounts.values()) {
                    System.out.println("Account: " + acc.accountID +
                            " | Bank: " + acc.bankName +
                            " | Balance: " + acc.balance);
                }
            }
        }
    
        // ===================== LOAD & SAVE ========================
        static void loadCategories() {
            try (Scanner file = new Scanner(new File("categories.txt"))) {
                while (file.hasNextLine()) {
                    categories.add(file.nextLine().trim());
                }
            } catch (Exception e) {
                // first run - file might not exist
            }
        }
    
        static void saveCategories() {
            try (PrintWriter pw = new PrintWriter("categories.txt")) {
                for (String cat : categories) pw.println(cat);
            } catch (Exception e) {
                System.out.println("Error saving categories.");
            }
        }
    
        static void loadAccounts() {
            try (Scanner file = new Scanner(new File("accounts.txt"))) {
                while (file.hasNextLine()) {
                    String[] parts = file.nextLine().split("\\s+");
                    if (parts.length >= 3) {
                        BankAccount acc = new BankAccount();
                        acc.accountID = parts[0];
                        acc.bankName = parts[1];
                        acc.balance = Double.parseDouble(parts[2]);
                        accounts.put(acc.accountID, acc);
                    }
                }
            } catch (Exception e) {}
        }
    
        static void saveAccounts() {
            try (PrintWriter pw = new PrintWriter("accounts.txt")) {
                for (BankAccount acc : accounts.values()) {
                    pw.println(acc.accountID + " " + acc.bankName + " " + acc.balance);
                }
            } catch (Exception e) {
                System.out.println("Error saving accounts.");
            }
        }
    
        static void loadExpenditures() {
            try (Scanner file = new Scanner(new File("expenditures.txt"))) {
                while (file.hasNextLine()) {
                    String[] parts = file.nextLine().split("\\s+");
                    if (parts.length >= 6) {
                        Expenditure exp = new Expenditure();
                        exp.code = parts[0];
                        exp.amount = Double.parseDouble(parts[1]);
                        exp.date = parts[2];
                        exp.phase = parts[3];
                        exp.category = parts[4];
                        exp.accountID = parts[5];
                        expenditures.put(exp.code, exp);
                        if (accounts.containsKey(exp.accountID)) {
                            accounts.get(exp.accountID).expenditureCodes.add(exp.code);
                        }
                    }
                }
            } catch (Exception e) {}
        }
    
        static void saveExpenditures() {
            try (PrintWriter pw = new PrintWriter("expenditures.txt")) {
                for (Expenditure exp : expenditures.values()) {
                    pw.println(exp.code + " " + exp.amount + " " + exp.date + " "
                            + exp.phase + " " + exp.category + " " + exp.accountID);
                }
            } catch (Exception e) {
                System.out.println("Error saving expenditures.");
            }
        }
    }
        
