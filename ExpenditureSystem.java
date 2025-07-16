import java.util.*;
import java.io.*;

public class ExpenditureSystem {
    private CategoryManager categoryManager = new CategoryManager();
    private BankLedger bankLedger = new BankLedger();
    private Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        ExpenditureSystem system = new ExpenditureSystem();
        system.loadInitialData();
        system.mainMenu();
    }
    
    private void loadInitialData() {
        try {
            categoryManager.loadFromFile("categories.txt");
            System.out.println("Loaded categories from file");
        } catch (IOException e) {
            System.out.println("No existing category data found. Starting with empty categories.");
        }
        
        try {
            bankLedger.loadFromFile("accounts.txt");
            System.out.println("Loaded bank accounts from file");
        } catch (IOException e) {
            System.out.println("No existing bank account data found. Starting with empty accounts.");
        }
    }
    
    // Main menu for the expenditure system
    private void mainMenu() {
        while (true) {
            System.out.println("\n===== NKWA REAL ESTATE - EXPENDITURE SYSTEM =====");
            System.out.println("1. Category Management");
            System.out.println("2. Bank Account Management");
            System.out.println("3. Save All Data");
            System.out.println("4. Exit System");
            System.out.print("Enter your choice: ");
    
            int choice = getIntInput();
    
            switch (choice) {
                case 1: categoryMenu(); break;
                case 2: bankAccountMenu(); break;
                case 3: saveAllData(); break;
                case 4: 
                    saveAllData();
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default: 
                    System.out.println("Invalid choice. Please enter 1-4.");
            }
        }
    }
    
    private void saveAllData() {
        try {
            categoryManager.saveToFile("categories.txt");
            bankLedger.saveToFile("accounts.txt");
            System.out.println("All data saved successfully:");
            System.out.println("- Categories saved to categories.txt");
            System.out.println("- Bank accounts saved to accounts.txt");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    private void categoryMenu() {
        while (true) {
            System.out.println("\n===== CATEGORY MANAGEMENT =====");
            System.out.println("1. Add New Category");
            System.out.println("2. Remove Category");
            System.out.println("3. Check Category Existence");
            System.out.println("4. List All Categories");
            System.out.println("5. Get Expenditure Code for Category");
            System.out.println("6. Get Category for Expenditure Code");
            System.out.println("7. Save Categories to File");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");
    
            int choice = getIntInput();
    
            switch (choice) {
                case 1: addCategory(); break;
                case 2: removeCategory(); break;
                case 3: checkCategory(); break;
                case 4: listCategories(); break;
                case 5: getCodeForCategory(); break;
                case 6: getCategoryForCode(); break;
                case 7: saveCategories(); break;
                case 8: return;
                default: 
                    System.out.println("Invalid choice. Please enter 1-8.");
            }
        }
    }
    
    private void addCategory() {
        System.out.print("Enter category name: ");
        String category = scanner.nextLine().trim();
        
        if (category.isEmpty()) {
            System.out.println("Category name cannot be empty");
            return;
        }
        
        System.out.print("Enter expenditure code (e.g., CAT001): ");
        String code = scanner.nextLine().trim();
        
        if (code.isEmpty()) {
            System.out.println("Expenditure code cannot be empty");
            return;
        }
        
        if (categoryManager.addCategory(category, code)) {
            System.out.println("Category added successfully");
        } else {
            System.out.println("Failed to add category (either name or code already exists)");
        }
    }
    
    private void getCodeForCategory() {
        System.out.print("Enter category name: ");
        String category = scanner.nextLine().trim();
        String code = categoryManager.getExpenditureCode(category);
        
        if (code != null) {
            System.out.println("Expenditure code for '" + category + "': " + code);
        } else {
            System.out.println("Category not found");
        }
    }
    
    private void getCategoryForCode() {
        System.out.print("Enter expenditure code: ");
        String code = scanner.nextLine().trim();
        String category = categoryManager.getCategoryByCode(code);
        
        if (category != null) {
            System.out.println("Category for code '" + code + "': " + category);
        } else {
            System.out.println("Expenditure code not found");
        }
    }
    
    private void saveCategories() {
        try {
            categoryManager.saveToFile("categories.txt");
            System.out.println("Categories saved successfully to categories.txt");
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }
    
    
    private void removeCategory() {
        System.out.print("Enter category name to remove: ");
        String category = scanner.nextLine().trim();
        
        if (categoryManager.removeCategory(category)) {
            System.out.println("Category removed successfully");
        } else {
            System.out.println("Category not found");
        }
    }
    
    private void checkCategory() {
        System.out.print("Enter category name to check: ");
        String category = scanner.nextLine().trim();
        
        if (categoryManager.containsCategory(category)) {
            System.out.println("Category exists in the system");
        } else {
            System.out.println("Category does not exist");
        }
    }
    
    private void listCategories() {
        List<String> categories = categoryManager.getAllCategoriesWithCodes();
        
        if (categories.isEmpty()) {
            System.out.println("No categories found");
            return;
        }
        
        System.out.println("\nAll Categories with Expenditure Codes:");
        System.out.println("------------------------------------");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, categories.get(i));
        }
    }
    
    private void bankAccountMenu() {
        while (true) {
            System.out.println("\n===== BANK ACCOUNT MANAGEMENT =====");
            System.out.println("1. Add New Bank Account");
            System.out.println("2. Record Expenditure");
            System.out.println("3. Link Two Accounts");
            System.out.println("4. View Account Details");
            System.out.println("5. View Account Relationships");
            System.out.println("6. Save Bank Accounts to File");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
    
            int choice = getIntInput();
    
            switch (choice) {
                case 1: addBankAccount(); break;
                case 2: recordExpenditure(); break;
                case 3: linkAccounts(); break;
                case 4: viewAccountDetails(); break;
                case 5: viewAccountRelationships(); break;
                case 6: saveBankAccounts(); break;
                case 7: return;
                default: 
                    System.out.println("Invalid choice. Please enter 1-7.");
            }
        }
    }
    
    private void saveBankAccounts() {
        try {
            bankLedger.saveToFile("accounts.txt");
            System.out.println("Bank accounts saved successfully to accounts.txt");
        } catch (IOException e) {
            System.out.println("Error saving bank accounts: " + e.getMessage());
        }
    }

    private void addBankAccount() {
        System.out.print("Enter Account ID: ");
        String accountId = scanner.nextLine().trim();

        if (bankLedger.accountExists(accountId)) {
            System.out.println("Account ID already exists");
            return;
        }

        System.out.print("Enter Bank Name: ");
        String bankName = scanner.nextLine().trim();

        System.out.print("Enter Initial Balance: ");
        double balance = getDoubleInput();

        if (balance < 0) {
            System.out.println("Balance cannot be negative");
            return;
        }

        if (bankLedger.addAccount(new BankAccount(accountId, bankName, balance))) {
            System.out.println("Account added successfully");
        } else {
            System.out.println("Failed to add account");
        }
    }

    private void recordExpenditure() {
        String accountId;
        boolean validAccount = false;
        
        // Keep asking for account ID until a valid one is entered
        do {
            System.out.print("Enter Account ID (or 'cancel' to abort): ");
            accountId = scanner.nextLine().trim();
            
            if (accountId.equalsIgnoreCase("cancel")) {
                System.out.println("Expenditure recording cancelled.");
                return;
            }
            
            if (!bankLedger.accountExists(accountId)) {
                System.out.println("Account not found. Please try again.");
            } else {
                validAccount = true;
            }
        } while (!validAccount);
        
        System.out.print("Enter Expenditure Code: ");
        String expCode = scanner.nextLine().trim();
        
        double amount;
        do {
            System.out.print("Enter Expenditure Amount (must be positive): ");
            amount = getDoubleInput();
            
            if (amount <= 0) {
                System.out.println("Amount must be positive. Please try again.");
            }
        } while (amount <= 0);
        
        if (bankLedger.recordExpenditure(accountId, expCode, amount)) {
            System.out.println("Expenditure recorded successfully to account: " + accountId);
        } else {
            System.out.println("Failed to record expenditure (insufficient funds)");
        }
    }

    private void linkAccounts() {
        String accountId1 = null;
        String accountId2 = null;
        boolean accountsValid = false;
    
        System.out.println("\n===== LINK BANK ACCOUNTS =====");
        
        // Get first account ID with validation
        while (!accountsValid) {
            System.out.print("Enter First Account ID (or 'cancel' to abort): ");
            accountId1 = scanner.nextLine().trim();
            
            if (accountId1.equalsIgnoreCase("cancel")) {
                System.out.println("Account linking cancelled.");
                return;
            }
            
            if (!bankLedger.accountExists(accountId1)) {
                System.out.println("Account not found. Please try again.");
                continue;
            }
    
            // Get second account ID with validation
            while (true) {
                System.out.print("Enter Second Account ID (or 'back' to re-enter first account): ");
                accountId2 = scanner.nextLine().trim();
                
                if (accountId2.equalsIgnoreCase("back")) {
                    break; // Go back to re-enter first account
                }
                
                if (accountId2.equalsIgnoreCase("cancel")) {
                    System.out.println("Account linking cancelled.");
                    return;
                }
                
                if (accountId2.equals(accountId1)) {
                    System.out.println("Cannot link an account to itself. Please try again.");
                    continue;
                }
                
                if (!bankLedger.accountExists(accountId2)) {
                    System.out.println("Account not found. Please try again.");
                    continue;
                }
                
                // Both accounts are valid
                accountsValid = true;
                break;
            }
        }
    
        // Attempt to link accounts
        if (bankLedger.linkAccounts(accountId1, accountId2)) {
            System.out.printf("Successfully linked accounts %s and %s%n", accountId1, accountId2);
        } else {
            System.out.println("Failed to link accounts. They may already be linked.");
        }
    }

    private void viewAccountDetails() {
        System.out.print("Enter Account ID: ");
        String accountId = scanner.nextLine().trim();

        BankAccount account = bankLedger.getAccount(accountId);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }

        System.out.println("\nAccount Details:");
        System.out.println(account);
    }

    private void viewAccountRelationships() {
        System.out.print("Enter Account ID: ");
        String accountId = scanner.nextLine().trim();

        if (!bankLedger.accountExists(accountId)) {
            System.out.println("Account not found");
            return;
        }

        List<String> links = bankLedger.getAccountLinks(accountId);
        if (links.isEmpty()) {
            System.out.println("No relationships found for this account");
        } else {
            System.out.println("\nAccounts linked to " + accountId + ":");
            for (String linkedAccount : links) {
                System.out.println("- " + linkedAccount);
            }
        }
    }

    
    // Helper methods for input validation
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}