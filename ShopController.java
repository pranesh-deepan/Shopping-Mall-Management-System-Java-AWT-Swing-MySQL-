import java.util.List;


public class ShopController {
    private DatabaseHandler dbHandler;

    
    public ShopController() {
        dbHandler = new DatabaseHandler();
    }

    
    public boolean addShop(Shop shop) {
        
        if (shop == null) {
            System.err.println("Shop object is null!");
            return false;
        }

        
        if (!validateShop(shop)) {
            return false;
        }

        return dbHandler.addShop(shop);
    }

    
    public List<Shop> getAllShops() {
        return dbHandler.getAllShops();
    }

    
    public Shop getShopById(int shopId) {
        if (shopId <= 0) {
            System.err.println("Invalid shop ID!");
            return null;
        }
        return dbHandler.getShopById(shopId);
    }

    
    public boolean updateShop(Shop shop) {
        if (shop == null) {
            System.err.println("Shop object is null!");
            return false;
        }

        if (shop.getShopId() <= 0) {
            System.err.println("Invalid shop ID for update!");
            return false;
        }

        
        if (!validateShop(shop)) {
            return false;
        }

        return dbHandler.updateShop(shop);
    }

    
    public boolean deleteShop(int shopId) {
        if (shopId <= 0) {
            System.err.println("Invalid shop ID for deletion!");
            return false;
        }

        return dbHandler.deleteShop(shopId);
    }

    
    public List<Shop> searchShopsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            System.err.println("Invalid category!");
            return null;
        }
        return dbHandler.searchShopsByCategory(category);
    }

    
    private boolean validateShop(Shop shop) {
        
        if (shop.getShopName() == null || shop.getShopName().trim().isEmpty()) {
            System.err.println("Shop name cannot be empty!");
            return false;
        }

        
        if (shop.getOwnerName() == null || shop.getOwnerName().trim().isEmpty()) {
            System.err.println("Owner name cannot be empty!");
            return false;
        }

        
        if (shop.getContactNumber() == null || shop.getContactNumber().trim().isEmpty()) {
            System.err.println("Contact number cannot be empty!");
            return false;
        }

        
        if (!shop.getContactNumber().matches("\\d{10}")) {
            System.err.println("Contact number must be 10 digits!");
            return false;
        }

        
        if (shop.getShopCategory() == null || shop.getShopCategory().trim().isEmpty()) {
            System.err.println("Shop category cannot be empty!");
            return false;
        }

        
        if (shop.getRentAmount() <= 0) {
            System.err.println("Rent amount must be greater than 0!");
            return false;
        }

        return true;
    }

    
    public int getTotalShops() {
        return getAllShops().size();
    }

    
    public double getTotalRentAmount() {
        List<Shop> shops = getAllShops();
        double total = 0;
        for (Shop shop : shops) {
            total += shop.getRentAmount();
        }
        return total;
    }

    
    public void closeDatabase() {
        if (dbHandler != null) {
            dbHandler.closeConnection();
        }
    }
}
