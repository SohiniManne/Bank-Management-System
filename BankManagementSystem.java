import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BankManagementSystem extends JFrame {
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private JComboBox<BankAccount> accountDropdown;
    private JTextField amountField;
    private JTextArea outputArea;

    public BankManagementSystem() {
        setTitle("Bank Management System");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Components
        JPanel panel = new JPanel(new GridLayout(8, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton createAccountBtn = new JButton("Create New Account");
        accountDropdown = new JComboBox<>();
        amountField = new JTextField();
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton viewBalanceBtn = new JButton("View Balance");
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add to panel
        panel.add(createAccountBtn);
        panel.add(accountDropdown);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(viewBalanceBtn);
        panel.add(scrollPane);

        add(panel);

        // Action Listeners
        createAccountBtn.addActionListener(e -> createAccount());
        depositBtn.addActionListener(e -> performTransaction(true));
        withdrawBtn.addActionListener(e -> performTransaction(false));
        viewBalanceBtn.addActionListener(e -> viewBalance());

        setVisible(true);
    }

    private void createAccount() {
        String name = JOptionPane.showInputDialog(this, "Enter account holder name:");
        String accNo = JOptionPane.showInputDialog(this, "Enter account number:");
        String balStr = JOptionPane.showInputDialog(this, "Enter initial balance:");
        try {
            double balance = Double.parseDouble(balStr);
            BankAccount acc = new BankAccount(name, accNo, balance);
            accounts.add(acc);
            accountDropdown.addItem(acc);
            outputArea.append("Account created for " + name + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void performTransaction(boolean isDeposit) {
        BankAccount acc = (BankAccount) accountDropdown.getSelectedItem();
        if (acc == null) {
            JOptionPane.showMessageDialog(this, "Select an account first.");
            return;
        }
        try {
            double amt = Double.parseDouble(amountField.getText());
            if (isDeposit) {
                acc.deposit(amt);
                outputArea.append("Deposited ₹" + amt + " to " + acc + "\n");
            } else {
                if (amt > acc.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds.");
                    return;
                }
                acc.withdraw(amt);
                outputArea.append("Withdrew ₹" + amt + " from " + acc + "\n");
            }
            amountField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void viewBalance() {
        BankAccount acc = (BankAccount) accountDropdown.getSelectedItem();
        if (acc != null) {
            outputArea.append("Balance of " + acc + ": ₹" + acc.getBalance() + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Select an account.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankManagementSystem());
    }
}
