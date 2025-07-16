package model;

public class Expenditure {
    private String code;
    private double amount;
    private String date;
    private String phase;
    private String category;
    private String accountId;

    public Expenditure(String code, double amount, String date, String phase, String category, String accountId) {
        this.code = code;
        this.amount = amount;
        this.date = date;
        this.phase = phase;
        this.category = category;
        this.accountId = accountId;
    }

    public String toFileString() {
        return String.join("|", code, String.valueOf(amount), date, phase, category, accountId);
    }

    public static Expenditure fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Expenditure(parts[0], Double.parseDouble(parts[1]), parts[2], parts[3], parts[4], parts[5]);
    }

    public String getCode() {
        return code;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getPhase() {
        return phase;
    }

    public String getCategory() {
        return category;
    }

    public String getAccountId() {
        return accountId;
    }
}
