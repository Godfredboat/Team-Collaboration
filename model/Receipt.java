package model;

public class Receipt {
    private String receiptId;
    private String expCode;
    private String status;

    public Receipt(String receiptId, String expCode, String status) {
        this.receiptId = receiptId;
        this.expCode = expCode;
        this.status = status;
    }

    public String toFileString() {
        return String.join("|", receiptId, expCode, status);
    }

    public static Receipt fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Receipt(parts[0], parts[1], parts[2]);
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getExpCode() {
        return expCode;
    }

    public String getStatus() {
        return status;
    }
}
