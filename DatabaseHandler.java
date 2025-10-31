import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopping_mall_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pranesh.d@123";

    private Connection connection;

    
    public DatabaseHandler() {
        
        boolean connected = tryConnectWithPassword() || tryConnectWithoutPassword();

        if (!connected) {
            System.err.println("CRITICAL: Could not connect to MySQL database!");
            System.err.println("Please run mysql_setup.bat as Administrator to configure the database.");
            System.err.println("Or manually set up MySQL with: mysql -u root -p < database_setup.sql");
        }
    }

    private boolean tryConnectWithPassword() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection with password failed, trying without password...");
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            return false;
        }
    }

    private boolean tryConnectWithoutPassword() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, "");
            System.out.println("Database connected successfully without password!");

            
            initializeDatabaseTables();

            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure MySQL server is running and the database is set up.");
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            return false;
        }
    }

    private void initializeDatabaseTables() {
        try {
            
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS shopping_mall_db");
            stmt.executeUpdate("USE shopping_mall_db");

            
            String createShopsTable = "CREATE TABLE IF NOT EXISTS shops (" +
                "shop_id INT PRIMARY KEY AUTO_INCREMENT," +
                "shop_name VARCHAR(100) NOT NULL," +
                "owner_name VARCHAR(100) NOT NULL," +
                "contact_number VARCHAR(15) NOT NULL," +
                "shop_category VARCHAR(50) NOT NULL," +
                "rent_amount DECIMAL(10, 2) NOT NULL," +
                "lease_start_date DATE NOT NULL," +
                "lease_end_date DATE NOT NULL," +
                "occupancy_status VARCHAR(20) NOT NULL DEFAULT 'Occupied'," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";

            stmt.executeUpdate(createShopsTable);

            
            String createRentPaymentsTable = "CREATE TABLE IF NOT EXISTS rent_payments (" +
                "payment_id INT PRIMARY KEY AUTO_INCREMENT," +
                "shop_id INT NOT NULL," +
                "payment_date DATE NOT NULL," +
                "amount_paid DECIMAL(10, 2) NOT NULL," +
                "payment_status VARCHAR(20) NOT NULL DEFAULT 'Pending'," +
                "due_date DATE NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (shop_id) REFERENCES shops(shop_id) ON DELETE CASCADE" +
                ")";

            stmt.executeUpdate(createRentPaymentsTable);

            stmt.close();
            System.out.println("Database tables initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Warning: Could not initialize database tables: " + e.getMessage());
        }
    }

    
    public Connection getConnection() {
        return connection;
    }

    
    public boolean addShop(Shop shop) {
        if (connection == null) {
            System.err.println("Database connection not available. Cannot add shop.");
            return false;
        }
        String query = "INSERT INTO shops (shop_name, owner_name, contact_number, shop_category, rent_amount, lease_start_date, lease_end_date, occupancy_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, shop.getShopName());
            pstmt.setString(2, shop.getOwnerName());
            pstmt.setString(3, shop.getContactNumber());
            pstmt.setString(4, shop.getShopCategory());
            pstmt.setDouble(5, shop.getRentAmount());
            pstmt.setString(6, shop.getLeaseStartDate());
            pstmt.setString(7, shop.getLeaseEndDate());
            pstmt.setString(8, shop.getOccupancyStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding shop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

   
    public List<Shop> getAllShops() {
        if (connection == null) {
            System.err.println("Database connection not available. Cannot retrieve shops.");
            return new ArrayList<>();
        }
        List<Shop> shops = new ArrayList<>();
        String query = "SELECT * FROM shops ORDER BY shop_id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Shop shop = new Shop(
                    rs.getInt("shop_id"),
                    rs.getString("shop_name"),
                    rs.getString("owner_name"),
                    rs.getString("contact_number"),
                    rs.getString("shop_category"),
                    rs.getDouble("rent_amount"),
                    rs.getString("lease_start_date"),
                    rs.getString("lease_end_date"),
                    rs.getString("occupancy_status")
                );
                shops.add(shop);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving shops: " + e.getMessage());
            e.printStackTrace();
        }

        return shops;
    }

    
    public Shop getShopById(int shopId) {
        String query = "SELECT * FROM shops WHERE shop_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, shopId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Shop(
                    rs.getInt("shop_id"),
                    rs.getString("shop_name"),
                    rs.getString("owner_name"),
                    rs.getString("contact_number"),
                    rs.getString("shop_category"),
                    rs.getDouble("rent_amount"),
                    rs.getString("lease_start_date"),
                    rs.getString("lease_end_date"),
                    rs.getString("occupancy_status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving shop: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

   
    public boolean updateShop(Shop shop) {
        String query = "UPDATE shops SET shop_name = ?, owner_name = ?, contact_number = ?, shop_category = ?, rent_amount = ? WHERE shop_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, shop.getShopName());
            pstmt.setString(2, shop.getOwnerName());
            pstmt.setString(3, shop.getContactNumber());
            pstmt.setString(4, shop.getShopCategory());
            pstmt.setDouble(5, shop.getRentAmount());
            pstmt.setInt(6, shop.getShopId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating shop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteShop(int shopId) {
        String query = "DELETE FROM shops WHERE shop_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, shopId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting shop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Shop> searchShopsByCategory(String category) {
        List<Shop> shops = new ArrayList<>();
        String query = "SELECT * FROM shops WHERE shop_category = ? ORDER BY shop_id";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Shop shop = new Shop(
                    rs.getInt("shop_id"),
                    rs.getString("shop_name"),
                    rs.getString("owner_name"),
                    rs.getString("contact_number"),
                    rs.getString("shop_category"),
                    rs.getDouble("rent_amount"),
                    rs.getString("lease_start_date"),
                    rs.getString("lease_end_date"),
                    rs.getString("occupancy_status")
                );
                shops.add(shop);
            }
        } catch (SQLException e) {
            System.err.println("Error searching shops: " + e.getMessage());
            e.printStackTrace();
        }

        return shops;
    }

    
    
    public boolean addRentPayment(RentPayment payment) {
        String query = "INSERT INTO rent_payments (shop_id, payment_date, amount_paid, payment_status, due_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, payment.getShopId());
            pstmt.setString(2, payment.getPaymentDate());
            pstmt.setDouble(3, payment.getAmountPaid());
            pstmt.setString(4, payment.getPaymentStatus());
            pstmt.setString(5, payment.getDueDate());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding rent payment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    
    public List<RentPayment> getAllRentPayments() {
        if (connection == null) {
            System.err.println("Database connection not available. Cannot retrieve rent payments.");
            return new ArrayList<>();
        }
        List<RentPayment> payments = new ArrayList<>();
        String query = "SELECT rp.*, s.shop_name FROM rent_payments rp JOIN shops s ON rp.shop_id = s.shop_id ORDER BY rp.payment_date DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                RentPayment payment = new RentPayment(
                    rs.getInt("payment_id"),
                    rs.getInt("shop_id"),
                    rs.getString("payment_date"),
                    rs.getDouble("amount_paid"),
                    rs.getString("payment_status"),
                    rs.getString("due_date"),
                    rs.getString("shop_name")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving rent payments: " + e.getMessage());
            e.printStackTrace();
        }

        return payments;
    }

    
    public List<RentPayment> getOverdueRentPayments() {
        List<RentPayment> payments = new ArrayList<>();
        String query = "SELECT rp.*, s.shop_name FROM rent_payments rp JOIN shops s ON rp.shop_id = s.shop_id WHERE rp.payment_status = 'Overdue' OR (rp.payment_status = 'Pending' AND rp.due_date < CURDATE()) ORDER BY rp.due_date";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                RentPayment payment = new RentPayment(
                    rs.getInt("payment_id"),
                    rs.getInt("shop_id"),
                    rs.getString("payment_date"),
                    rs.getDouble("amount_paid"),
                    rs.getString("payment_status"),
                    rs.getString("due_date"),
                    rs.getString("shop_name")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving overdue payments: " + e.getMessage());
            e.printStackTrace();
        }

        return payments;
    }

    
    public boolean updateRentPaymentStatus(int paymentId, String status) {
        String query = "UPDATE rent_payments SET payment_status = ? WHERE payment_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, paymentId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    
    public double getTotalRentThisMonth() {
        if (connection == null) {
            System.err.println("Database connection not available. Cannot calculate total rent.");
            return 0.0;
        }
        String query = "SELECT SUM(amount_paid) as total FROM rent_payments WHERE MONTH(payment_date) = MONTH(CURDATE()) AND YEAR(payment_date) = YEAR(CURDATE()) AND payment_status = 'Paid'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total rent this month: " + e.getMessage());
            e.printStackTrace();
        }

        return 0.0;
    }

    
    public double getTotalPendingRent() {
        if (connection == null) {
            System.err.println("Database connection not available. Cannot calculate pending rent.");
            return 0.0;
        }
        String query = "SELECT SUM(s.rent_amount) as total FROM shops s JOIN rent_payments rp ON s.shop_id = rp.shop_id WHERE rp.payment_status IN ('Pending', 'Overdue') AND MONTH(rp.due_date) = MONTH(CURDATE()) AND YEAR(rp.due_date) = YEAR(CURDATE())";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total pending rent: " + e.getMessage());
            e.printStackTrace();
        }

        return 0.0;
    }

    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
