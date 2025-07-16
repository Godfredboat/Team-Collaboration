import java.util.*;

public class BankAccount {
    private String accountId;
    private String bankName;
    private double balance;
    private List<String> expenditureCodes = new LinkedList<>();
    
    public BankAccount(String accountId, String bankName, double balance) {
        this.accountId = accountId;
        this.bankName = bankName;
        this.balance = balance;
    }
    
    public void addExpenditure(String expCode, double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds in account " + accountId);
        }
        balance -= amount;
        expenditureCodes.add(expCode);
    }
    
    // Getters
    public String getAccountId() { return accountId; }
    public String getBankName() { return bankName; }
    public double getBalance() { return balance; }
    public List<String> getExpenditureCodes() { return expenditureCodes; }
    
    @Override
    public String toString() {
        return String.format("Account ID: %s\nBank: %s\nBalance: GHS %,.2f\nExpenditures: %s",
                accountId, bankName, balance, expenditureCodes);
    }
}