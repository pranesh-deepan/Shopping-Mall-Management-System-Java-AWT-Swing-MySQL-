import javax.swing.*;

/**
 * ShoppingMallApp Class
 * Main entry point for the Shopping Mall Management System
 */
public class ShoppingMallApp {
    public static void main(String[] args) {
        // Set Look and Feel to system default for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set Look and Feel: " + e.getMessage());
        }

        // Display splash screen with loading message
        displaySplashScreen();

        // Create controller instance
        ShopController controller = new ShopController();

        // Launch main window
        SwingUtilities.invokeLater(() -> {
            new MainWindow(controller);
        });
    }

    private static void displaySplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(400, 250);
        splash.setLocationRelativeTo(null);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new java.awt.Color(41, 128, 185));
        content.setBorder(BorderFactory.createLineBorder(new java.awt.Color(52, 73, 94), 3));

        // Title
        JLabel title = new JLabel("Shopping Mall Management System");
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        title.setForeground(java.awt.Color.WHITE);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Complete CRUD Operations");
        subtitle.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        subtitle.setForeground(java.awt.Color.WHITE);
        subtitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Version
        JLabel version = new JLabel("Version 1.0");
        version.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 12));
        version.setForeground(java.awt.Color.WHITE);
        version.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Loading message
        JLabel loading = new JLabel("Loading...");
        loading.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        loading.setForeground(java.awt.Color.WHITE);
        loading.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Add components with spacing
        content.add(Box.createVerticalGlue());
        content.add(title);
        content.add(Box.createVerticalStrut(10));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(5));
        content.add(version);
        content.add(Box.createVerticalStrut(30));
        content.add(loading);
        content.add(Box.createVerticalGlue());

        splash.setContentPane(content);
        splash.setVisible(true);

        // Display splash for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.dispose();
    }
}
