import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RentManagementWindow extends JFrame {
    private DatabaseHandler dbHandler;
    private JTable rentTable;
    private DefaultTableModel tableModel;
    private JTextField shopIdField, amountField, dueDateField;
    private JComboBox<String> statusCombo;
    private JLabel totalCollectedLabel, totalPendingLabel;

    public RentManagementWindow() {
        dbHandler = new DatabaseHandler();

        setTitle("Rent Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadRentData();
        updateSummaryLabels();

        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Rent Summary"));
        summaryPanel.setBackground(Color.LIGHT_GRAY);

        totalCollectedLabel = new JLabel("Total Collected This Month: $0.00");
        totalPendingLabel = new JLabel("Total Pending: $0.00");

        summaryPanel.add(totalCollectedLabel);
        summaryPanel.add(totalPendingLabel);

        add(summaryPanel, BorderLayout.NORTH);

        
        String[] columnNames = {"Payment ID", "Shop Name", "Amount Paid", "Payment Date", "Due Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        rentTable = new JTable(tableModel);
        rentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(rentTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

    
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("Shop ID:"), gbc);
        gbc.gridx = 1;
        shopIdField = new JTextField(10);
        controlPanel.add(shopIdField, gbc);

        
        gbc.gridx = 2; gbc.gridy = 0;
        controlPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 3;
        amountField = new JTextField(10);
        controlPanel.add(amountField, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dueDateField = new JTextField(10);
        controlPanel.add(dueDateField, gbc);

        
        gbc.gridx = 2; gbc.gridy = 1;
        controlPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        statusCombo = new JComboBox<>(new String[]{"Paid", "Pending", "Overdue"});
        controlPanel.add(statusCombo, gbc);

        
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addPaymentBtn = new JButton("Add Payment");
        addPaymentBtn.addActionListener(new AddPaymentListener());
        buttonPanel.add(addPaymentBtn);

        JButton markPaidBtn = new JButton("Mark as Paid");
        markPaidBtn.addActionListener(new MarkPaidListener());
        buttonPanel.add(markPaidBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            loadRentData();
            updateSummaryLabels();
        });
        buttonPanel.add(refreshBtn);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        controlPanel.add(buttonPanel, gbc);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void loadRentData() {
        tableModel.setRowCount(0);
        List<RentPayment> payments = dbHandler.getAllRentPayments();

        for (RentPayment payment : payments) {
            Object[] row = {
                payment.getPaymentId(),
                payment.getShopName(),
                String.format("$%.2f", payment.getAmountPaid()),
                payment.getPaymentDate(),
                payment.getDueDate(),
                payment.getPaymentStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void updateSummaryLabels() {
        double totalCollected = dbHandler.getTotalRentThisMonth();
        double totalPending = dbHandler.getTotalPendingRent();

        totalCollectedLabel.setText(String.format("Total Collected This Month: $%.2f", totalCollected));
        totalPendingLabel.setText(String.format("Total Pending: $%.2f", totalPending));
    }

    private class AddPaymentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int shopId = Integer.parseInt(shopIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String dueDate = dueDateField.getText();
                String status = (String) statusCombo.getSelectedItem();

                // Set payment date to today
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String paymentDate = sdf.format(new Date());

                RentPayment payment = new RentPayment(0, shopId, paymentDate, amount, status, dueDate);
                if (dbHandler.addRentPayment(payment)) {
                    JOptionPane.showMessageDialog(RentManagementWindow.this, "Payment added successfully!");
                    loadRentData();
                    updateSummaryLabels();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(RentManagementWindow.this, "Failed to add payment.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(RentManagementWindow.this, "Please enter valid numbers for Shop ID and Amount.");
            }
        }
    }

    private class MarkPaidListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = rentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(RentManagementWindow.this, "Please select a payment to mark as paid.");
                return;
            }

            int paymentId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (dbHandler.updateRentPaymentStatus(paymentId, "Paid")) {
                JOptionPane.showMessageDialog(RentManagementWindow.this, "Payment marked as paid!");
                loadRentData();
                updateSummaryLabels();
            } else {
                JOptionPane.showMessageDialog(RentManagementWindow.this, "Failed to update payment status.");
            }
        }
    }

    private void clearFields() {
        shopIdField.setText("");
        amountField.setText("");
        dueDateField.setText("");
        statusCombo.setSelectedIndex(0);
    }
}
