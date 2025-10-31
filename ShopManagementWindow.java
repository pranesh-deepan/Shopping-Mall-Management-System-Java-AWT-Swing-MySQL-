import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ShopManagementWindow extends Frame implements ActionListener {
    private ShopController controller;
    
    
    private TextField txtShopId;
    private TextField txtShopName;
    private TextField txtOwnerName;
    private TextField txtContactNumber;
    private Choice choiceCategory;
    private TextField txtRentAmount;
    
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnClear;
    private Button btnSearch;
    private Button btnClose;
    
    private Label lblStatus;

    public ShopManagementWindow(ShopController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        
        setTitle("Manage Shops");
        setSize(700, 550);
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(41, 128, 185));
        Label lblTitle = new Label("SHOP MANAGEMENT", Label.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        
       
        Panel formContainer = new Panel();
        formContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        formContainer.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(600, 300));

        
        Label lblShopId = new Label("Shop ID:");
        lblShopId.setFont(new Font("Arial", Font.BOLD, 14));
        txtShopId = new TextField(20);
        txtShopId.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblShopId);
        formPanel.add(txtShopId);

        
        Label lblShopName = new Label("Shop Name:");
        lblShopName.setFont(new Font("Arial", Font.BOLD, 14));
        txtShopName = new TextField(20);
        txtShopName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblShopName);
        formPanel.add(txtShopName);

        
        Label lblOwnerName = new Label("Owner Name:");
        lblOwnerName.setFont(new Font("Arial", Font.BOLD, 14));
        txtOwnerName = new TextField(20);
        txtOwnerName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblOwnerName);
        formPanel.add(txtOwnerName);

        
        Label lblContactNumber = new Label("Contact Number:");
        lblContactNumber.setFont(new Font("Arial", Font.BOLD, 14));
        txtContactNumber = new TextField(20);
        txtContactNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblContactNumber);
        formPanel.add(txtContactNumber);

        
        Label lblCategory = new Label("Shop Category:");
        lblCategory.setFont(new Font("Arial", Font.BOLD, 14));
        choiceCategory = new Choice();
        choiceCategory.add("Clothing");
        choiceCategory.add("Electronics");
        choiceCategory.add("Food");
        choiceCategory.add("Books");
        choiceCategory.add("Jewelry");
        choiceCategory.add("Sports");
        choiceCategory.add("Toys");
        choiceCategory.add("Furniture");
        choiceCategory.add("Beauty");
        choiceCategory.add("Others");
        choiceCategory.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblCategory);
        formPanel.add(choiceCategory);

        
        Label lblRentAmount = new Label("Rent Amount:");
        lblRentAmount.setFont(new Font("Arial", Font.BOLD, 14));
        txtRentAmount = new TextField(20);
        txtRentAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblRentAmount);
        formPanel.add(txtRentAmount);

        
        lblStatus = new Label("Ready", Label.CENTER);
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setForeground(new Color(52, 152, 219));
        formPanel.add(new Label("")); 
        formPanel.add(lblStatus);

        formContainer.add(formPanel);
        add(formContainer, BorderLayout.CENTER);

        
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(240, 240, 240));

       
        btnAdd = new Button("Add Shop");
        btnUpdate = new Button("Update Shop");
        btnDelete = new Button("Delete Shop");
        btnSearch = new Button("Search by ID");
        btnClear = new Button("Clear");
        btnClose = new Button("Close");

        
        styleButton(btnAdd, new Color(46, 204, 113));
        styleButton(btnUpdate, new Color(52, 152, 219));
        styleButton(btnDelete, new Color(231, 76, 60));
        styleButton(btnSearch, new Color(241, 196, 15));
        styleButton(btnClear, new Color(149, 165, 166));
        styleButton(btnClose, new Color(127, 140, 141));

        
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);
        btnClear.addActionListener(this);
        btnClose.addActionListener(this);

        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(Button btn, Color color) {
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addShop();
        } else if (e.getSource() == btnUpdate) {
            updateShop();
        } else if (e.getSource() == btnDelete) {
            deleteShop();
        } else if (e.getSource() == btnSearch) {
            searchShop();
        } else if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnClose) {
            dispose();
        }
    }

    private void addShop() {
        if (!validateInputs(false)) {
            return;
        }

        try {
            Shop shop = new Shop(
                txtShopName.getText().trim(),
                txtOwnerName.getText().trim(),
                txtContactNumber.getText().trim(),
                choiceCategory.getSelectedItem(),
                Double.parseDouble(txtRentAmount.getText().trim()),
                "2023-01-01",
                "2024-01-01", 
                "Occupied" 
            );

            if (controller.addShop(shop)) {
                lblStatus.setText("Shop added successfully!");
                lblStatus.setForeground(new Color(46, 204, 113));
                JOptionPane.showMessageDialog(this, "Shop added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                lblStatus.setText("Failed to add shop!");
                lblStatus.setForeground(new Color(231, 76, 60));
                JOptionPane.showMessageDialog(this, "Failed to add shop!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setForeground(new Color(231, 76, 60));
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateShop() {
        if (!validateInputs(true)) {
            return;
        }

        try {
            int shopId = Integer.parseInt(txtShopId.getText().trim());
            Shop shop = new Shop(
                shopId,
                txtShopName.getText().trim(),
                txtOwnerName.getText().trim(),
                txtContactNumber.getText().trim(),
                choiceCategory.getSelectedItem(),
                Double.parseDouble(txtRentAmount.getText().trim()),
                "2023-01-01", 
                "2024-01-01", 
                "Occupied" 
            );

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to update this shop?",
                "Update Confirmation",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.updateShop(shop)) {
                    lblStatus.setText("Shop updated successfully!");
                    lblStatus.setForeground(new Color(46, 204, 113));
                    JOptionPane.showMessageDialog(this, "Shop updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    lblStatus.setText("Failed to update shop!");
                    lblStatus.setForeground(new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(this, "Failed to update shop!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setForeground(new Color(231, 76, 60));
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteShop() {
        String shopIdStr = txtShopId.getText().trim();
        
        if (shopIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Shop ID to delete!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int shopId = Integer.parseInt(shopIdStr);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this shop?\nThis action cannot be undone!",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteShop(shopId)) {
                    lblStatus.setText("Shop deleted successfully!");
                    lblStatus.setForeground(new Color(46, 204, 113));
                    JOptionPane.showMessageDialog(this, "Shop deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    lblStatus.setText("Failed to delete shop!");
                    lblStatus.setForeground(new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(this, "Shop not found or failed to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Shop ID must be a valid number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchShop() {
        String shopIdStr = txtShopId.getText().trim();
        
        if (shopIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Shop ID to search!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int shopId = Integer.parseInt(shopIdStr);
            Shop shop = controller.getShopById(shopId);
            
            if (shop != null) {
                txtShopName.setText(shop.getShopName());
                txtOwnerName.setText(shop.getOwnerName());
                txtContactNumber.setText(shop.getContactNumber());
                choiceCategory.select(shop.getShopCategory());
                txtRentAmount.setText(String.valueOf(shop.getRentAmount()));
                
                lblStatus.setText("Shop found!");
                lblStatus.setForeground(new Color(46, 204, 113));
            } else {
                lblStatus.setText("Shop not found!");
                lblStatus.setForeground(new Color(231, 76, 60));
                JOptionPane.showMessageDialog(this, "Shop with ID " + shopId + " not found!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Shop ID must be a valid number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        txtShopId.setText("");
        txtShopName.setText("");
        txtOwnerName.setText("");
        txtContactNumber.setText("");
        choiceCategory.select(0);
        txtRentAmount.setText("");
        lblStatus.setText("Ready");
        lblStatus.setForeground(new Color(52, 152, 219));
    }

    private boolean validateInputs(boolean requireShopId) {
        if (requireShopId && txtShopId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Shop ID!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtShopName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Shop Name!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtOwnerName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Owner Name!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String contact = txtContactNumber.getText().trim();
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Contact Number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact Number must be 10 digits!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String rentStr = txtRentAmount.getText().trim();
        if (rentStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Rent Amount!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            double rent = Double.parseDouble(rentStr);
            if (rent <= 0) {
                JOptionPane.showMessageDialog(this, "Rent Amount must be greater than 0!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Rent Amount must be a valid number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }
}
