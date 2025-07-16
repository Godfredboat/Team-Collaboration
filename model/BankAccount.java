package model;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accId;
    private String bankName;
    private double balance;
    private List<String> expenditures;

    public BankAccount(String accId, String bankName, double balance) {
        this.accId = accId;
        this.bankName = bankName;
        this.balance = balance;
        this.expenditures = new ArrayList<>();
    }

    public void addExpenditure(String expCode) {
        expenditures.add(expCode);
    }

    public String toFileString() {
        return String.join("|", accId, bankName, String.valueOf(balance));
    }

    public static BankAccount fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new BankAccount(parts[0], parts[1], Double.parseDouble(parts[2]));
    }

    public String getAccId() {
        return accId;
    }

    public String getBankName() {
        return bankName;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance -= amount;
    }

    public List<String> getExpenditures() {
        return expenditures;
    }
}
