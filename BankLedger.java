import java.util.*;
import java.io.*;

public class BankLedger {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private Map<String, List<String>> accountGraph = new HashMap<>();

    // Adds a new account, returns true if successful
    public boolean addAccount(BankAccount account) {
        if (account == null || account.getAccountId() == null) {
            return false;
        }
        if (accounts.containsKey(account.getAccountId())) {
            return false;
        }
        accounts.put(account.getAccountId(), account);
        accountGraph.put(account.getAccountId(), new ArrayList<>());
        return true;
    }

    // Links two accounts together
    public boolean linkAccounts(String accountId1, String accountId2) {
        if (!accounts.containsKey(accountId1) || !accounts.containsKey(accountId2)) {
            return false;
        }

        List<String> links1 = accountGraph.get(accountId1);
        List<String> links2 = accountGraph.get(accountId2);

        if (!links1.contains(accountId2)) {
            links1.add(accountId2);
        }
        if (!links2.contains(accountId1)) {
            links2.add(accountId1);
        }
        return true;
    }

    // Records an expenditure against an account
    public boolean recordExpenditure(String accountId, String expCode, double amount) {
        BankAccount account = accounts.get(accountId);
        if (account == null) {
            return false;
        }
        try {
            account.addExpenditure(expCode, amount);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Gets an account or returns null if not found
    public BankAccount getAccount(String accountId) {
        return accounts.get(accountId);
    }

    // Gets linked accounts or empty list if none
    public List<String> getAccountLinks(String accountId) {
        List<String> links = accountGraph.get(accountId);
        return links != null ? links : Collections.emptyList();
    }

    // Checks if account exists
    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }

    // File I/O methods
    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (BankAccount account : accounts.values()) {
                writer.printf("%s,%s,%.2f%n", 
                    account.getAccountId(), 
                    account.getBankName(), 
                    account.getBalance());
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        accounts.clear();
        accountGraph.clear();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3) {
                    String accountId = data[0].trim();
                    String bankName = data[1].trim();
                    double balance = Double.parseDouble(data[2].trim());
                    
                    addAccount(new BankAccount(accountId, bankName, balance));
                }
            }
        }
    }
}