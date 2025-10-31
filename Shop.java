
public class Shop {
    private int shopId;
    private String shopName;
    private String ownerName;
    private String contactNumber;
    private String shopCategory;
    private String leaseStartDate;
    private String leaseEndDate;
    private String occupancyStatus;
    private double rentAmount;

    
    public Shop() {
    }

    
    public Shop(int shopId, String shopName, String ownerName, String contactNumber,
                String shopCategory, double rentAmount, String leaseStartDate, String leaseEndDate, String occupancyStatus) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.shopCategory = shopCategory;
        this.rentAmount = rentAmount;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.occupancyStatus = occupancyStatus;
    }

    
    public Shop(String shopName, String ownerName, String contactNumber,
                String shopCategory, double rentAmount, String leaseStartDate, String leaseEndDate, String occupancyStatus) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.shopCategory = shopCategory;
        this.rentAmount = rentAmount;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.occupancyStatus = occupancyStatus;
    }

    
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public String getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public String getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(String leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public String getOccupancyStatus() {
        return occupancyStatus;
    }

    public void setOccupancyStatus(String occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", shopCategory='" + shopCategory + '\'' +
                ", rentAmount=" + rentAmount +
                ", leaseStartDate='" + leaseStartDate + '\'' +
                ", leaseEndDate='" + leaseEndDate + '\'' +
                ", occupancyStatus='" + occupancyStatus + '\'' +
                '}';
    }
}
