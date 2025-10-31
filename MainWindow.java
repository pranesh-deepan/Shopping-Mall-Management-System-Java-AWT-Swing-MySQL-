import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends Frame implements ActionListener {
    private ShopController controller;
    
    
    private Button btnManageShops;
    private Button btnViewShops;
    private Button btnRentManagement;
    private Button btnExit;
    private Label lblTitle;
    private Panel mainPanel;
    private Panel buttonPanel;

    public MainWindow(ShopController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        
        setTitle("Shopping Mall Management System");
        setSize(800, 500);  
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(41, 128, 185));
        lblTitle = new Label("SHOPPING MALL MANAGEMENT SYSTEM", Label.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        
        mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        Label welcomeLabel = new Label("Welcome to Shopping Mall Management System", Label.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        
        Panel infoPanel = new Panel();
        infoPanel.setLayout(new GridLayout(4, 1, 5, 5));
        infoPanel.setBackground(Color.WHITE);
        
        Label info1 = new Label("• Manage shop details efficiently", Label.LEFT);
        Label info2 = new Label("• Add, Update, Delete shop records", Label.LEFT);
        Label info3 = new Label("• View all shops in organized table", Label.LEFT);
        Label info4 = new Label("• Track rent and contact information", Label.LEFT);
        
        info1.setFont(new Font("Arial", Font.PLAIN, 14));
        info2.setFont(new Font("Arial", Font.PLAIN, 14));
        info3.setFont(new Font("Arial", Font.PLAIN, 14));
        info4.setFont(new Font("Arial", Font.PLAIN, 14));
        
        infoPanel.add(info1);
        infoPanel.add(info2);
        infoPanel.add(info3);
        infoPanel.add(info4);
        
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        
        buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));  
        buttonPanel.setBackground(new Color(240, 240, 240));

        
        btnManageShops = new Button("Manage Shops");
        btnViewShops = new Button("View All Shops");
        btnRentManagement = new Button("Rent Management");
        btnExit = new Button("Exit");

       
        styleButton(btnManageShops, new Color(46, 204, 113));
        styleButton(btnViewShops, new Color(52, 152, 219));
        styleButton(btnRentManagement, new Color(155, 89, 182));
        styleButton(btnExit, new Color(231, 76, 60));

        
        btnManageShops.addActionListener(this);
        btnViewShops.addActionListener(this);
        btnRentManagement.addActionListener(this);
        btnExit.addActionListener(this);

        
        buttonPanel.add(btnManageShops);
        buttonPanel.add(btnViewShops);
        buttonPanel.add(btnRentManagement);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.SOUTH);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                exitApplication();
            }
        });

        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(Button btn, Color color) {
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnManageShops) {
            new ShopManagementWindow(controller);
        } else if (e.getSource() == btnViewShops) {
            new ViewShopsWindow(controller);
        } else if (e.getSource() == btnRentManagement) {
            System.out.println("Rent Management button clicked");
            new RentManagementWindow();
        } else if (e.getSource() == btnExit) {
            exitApplication();
        }
    }

    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            controller.closeDatabase();
            System.exit(0);
        }
    }
}
