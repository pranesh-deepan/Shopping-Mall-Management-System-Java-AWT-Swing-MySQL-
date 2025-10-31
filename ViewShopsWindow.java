import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * ViewShopsWindow Class
 * Window for displaying all shop records in a table
 */
public class ViewShopsWindow extends Frame implements ActionListener {
    private ShopController controller;
    
    // UI Components
    private JTable shopTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private Button btnRefresh;
    private Button btnClose;
    private Label lblTotalShops;

    public ViewShopsWindow(ShopController controller) {
        this.controller = controller;
        initializeUI();
        loadShopData();
    }

    private void initializeUI() {
        // Set frame properties
        setTitle("View All Shops");
        setSize(900, 500);
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // Title Panel
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(41, 128, 185));
        Label lblTitle = new Label("ALL SHOPS IN MALL", Label.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        Panel tablePanel = new Panel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Create table model with column names
        String[] columnNames = {"Shop ID", "Shop Name", "Owner Name", "Contact Number", "Category", "Rent Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Create table
        shopTable = new JTable(tableModel);
        shopTable.setFont(new Font("Arial", Font.PLAIN, 13));
        shopTable.setRowHeight(25);
        shopTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        shopTable.getTableHeader().setBackground(new Color(52, 152, 219));
        shopTable.getTableHeader().setForeground(Color.WHITE);
        shopTable.setSelectionBackground(new Color(52, 152, 219));
        shopTable.setSelectionForeground(Color.WHITE);
        shopTable.setGridColor(new Color(189, 195, 199));

        // Set column widths
        shopTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        shopTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        shopTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        shopTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        shopTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        shopTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Add table to scroll pane
        scrollPane = new JScrollPane(shopTable);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel with info and buttons
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 240));

        // Info Panel
        Panel infoPanel = new Panel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        infoPanel.setBackground(new Color(240, 240, 240));
        
        lblTotalShops = new Label("Total Shops: 0");
        lblTotalShops.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalShops.setForeground(new Color(41, 128, 185));
        infoPanel.add(lblTotalShops);
        
        bottomPanel.add(infoPanel, BorderLayout.WEST);

        // Button Panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Create buttons
        btnRefresh = new Button("Refresh");
        btnClose = new Button("Close");

        // Style buttons
        styleButton(btnRefresh, new Color(46, 204, 113));
        styleButton(btnClose, new Color(127, 140, 141));

        // Add action listeners
        btnRefresh.addActionListener(this);
        btnClose.addActionListener(this);

        // Add buttons to panel
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        // Center the window on screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(Button btn, Color color) {
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void loadShopData() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Get all shops from controller
        List<Shop> shops = controller.getAllShops();

        // Add rows to table
        for (Shop shop : shops) {
            Object[] row = {
                shop.getShopId(),
                shop.getShopName(),
                shop.getOwnerName(),
                shop.getContactNumber(),
                shop.getShopCategory(),
                String.format("%.2f", shop.getRentAmount())
            };
            tableModel.addRow(row);
        }

        // Update total count
        lblTotalShops.setText("Total Shops: " + shops.size());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRefresh) {
            loadShopData();
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == btnClose) {
            dispose();
        }
    }
}
