// File: Expenditure.java
import java.time.LocalDate;

public class Expenditure {
    private String code;
    private double amount;
    private LocalDate date;
    private String phase;
    private String category;
    private String bankAccount;

    public Expenditure(String code, double amount, LocalDate date, String phase, String category, String bankAccount) {
        this.code = code;
        this.amount = amount;
        this.date = date;
        this.phase = phase;
        this.category = category;
        this.bankAccount = bankAccount;
    }

    public String getCode() { return code; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getPhase() { return phase; }
    public String getCategory() { return category; }
    public String getBankAccount() { return bankAccount; }
}

// File: Receipt.java
import java.time.LocalDate;

public class Receipt {
    private String expenditureCode;
    private String fileName;
    private LocalDate uploadDate;

    public Receipt(String expenditureCode, String fileName, LocalDate uploadDate) {
        this.expenditureCode = expenditureCode;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
    }

    public String getExpenditureCode() { return expenditureCode; }
    public String getFileName() { return fileName; }
    public LocalDate getUploadDate() { return uploadDate; }
}

// File: ExpenditureManager.java
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenditureManager {
    private HashMap<String, Expenditure> expenditureMap = new HashMap<>();

    public void addExpenditure(Expenditure exp) {
        expenditureMap.put(exp.getCode(), exp);
        saveToFile(exp);
    }

    public Expenditure getByCode(String code) {
        return expenditureMap.get(code);
    }

    public List<Expenditure> searchByCategory(String category) {
        return expenditureMap.values().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Expenditure> searchByDateRange(LocalDate start, LocalDate end) {
        return expenditureMap.values().stream()
                .filter(e -> !e.getDate().isBefore(start) && !e.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public List<Expenditure> searchByAmountRange(double min, double max) {
        return expenditureMap.values().stream()
                .filter(e -> e.getAmount() >= min && e.getAmount() <= max)
                .collect(Collectors.toList());
    }

    public List<Expenditure> searchByBankAccount(String accountId) {
        return expenditureMap.values().stream()
                .filter(e -> e.getBankAccount().equals(accountId))
                .collect(Collectors.toList());
    }

    public List<Expenditure> sortByCategory() {
        return expenditureMap.values().stream()
                .sorted(Comparator.comparing(Expenditure::getCategory))
                .collect(Collectors.toList());
    }

    public List<Expenditure> sortByDate() {
        return expenditureMap.values().stream()
                .sorted(Comparator.comparing(Expenditure::getDate))
                .collect(Collectors.toList());
    }

    private void saveToFile(Expenditure exp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenditures.txt", true))) {
            writer.write(String.format("%s|%.2f|%s|%s|%s|%s\n",
                    exp.getCode(), exp.getAmount(), exp.getDate(), exp.getPhase(), exp.getCategory(), exp.getBankAccount()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// File: ReceiptManager.java
import java.io.*;
import java.util.*;

public class ReceiptManager {
    private Stack<Receipt> receiptStack = new Stack<>();

    public void uploadReceipt(Receipt receipt) {
        receiptStack.push(receipt);
        saveToFile(receipt);
    }

    public Receipt reviewNextReceipt() {
        if (!receiptStack.isEmpty()) {
            return receiptStack.pop();
        }
        return null;
    }

    private void saveToFile(Receipt receipt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("receipts.txt", true))) {
            writer.write(String.format("%s|%s|%s\n", receipt.getExpenditureCode(), receipt.getFileName(), receipt.getUploadDate()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
