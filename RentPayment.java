
public class RentPayment {
    private int paymentId;
    private int shopId;
    private String paymentDate;
    private double amountPaid;
    private String paymentStatus;
    private String dueDate;
    private String shopName;

    
    public RentPayment(int paymentId, int shopId, String paymentDate, double amountPaid, String paymentStatus, String dueDate) {
        this.paymentId = paymentId;
        this.shopId = shopId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
    }

    
    public RentPayment(int paymentId, int shopId, String paymentDate, double amountPaid, String paymentStatus, String dueDate, String shopName) {
        this.paymentId = paymentId;
        this.shopId = shopId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentStatus = paymentStatus;
        this.dueDate = dueDate;
        this.shopName = shopName;
    }

    
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getShopId() { return shopId; }
    public void setShopId(int shopId) { this.shopId = shopId; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
}
