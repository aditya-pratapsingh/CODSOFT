import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            JOptionPane.showMessageDialog(null, "₹" + amount + " deposited successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "₹" + amount + " withdrawn successfully!");
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient balance!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid withdrawal amount!");
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATMInterface extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField inputField;
    private JButton[] numButtons;
    private JButton withdrawBtn, depositBtn, balanceBtn, clearBtn, enterBtn, cancelBtn;
    private JLabel screenLabel;
    private String currentAction = "";
    private boolean pinVerified = false;
    private final String correctPIN = "1234"; 

    public ATMInterface(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(25, 118, 210));
        JLabel title = new JLabel("ATM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        topPanel.add(title);

        JPanel screenPanel = new JPanel(new BorderLayout());
        screenPanel.setBackground(Color.WHITE);
        screenPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        screenLabel = new JLabel("Enter PIN to Access ATM", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenPanel.add(screenLabel, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        withdrawBtn = new JButton("Withdraw");
        depositBtn = new JButton("Deposit");
        balanceBtn = new JButton("Check Balance");
        JButton[] mainButtons = {withdrawBtn, depositBtn, balanceBtn};
        for (JButton btn : mainButtons) {
            btn.setBackground(new Color(33, 150, 243));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(this);
            menuPanel.add(btn);
        }

        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        numButtons = new JButton[10];
        for (int i = 1; i <= 9; i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(this);
            keypadPanel.add(numButtons[i]);
        }
        clearBtn = new JButton("Clear");
        clearBtn.setBackground(Color.YELLOW);
        clearBtn.addActionListener(this);
        keypadPanel.add(clearBtn);

        numButtons[0] = new JButton("0");
        numButtons[0].addActionListener(this);
        keypadPanel.add(numButtons[0]);

        enterBtn = new JButton("Enter");
        enterBtn.setBackground(Color.GREEN);
        enterBtn.addActionListener(this);
        keypadPanel.add(enterBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(this);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setFont(new Font("Arial", Font.BOLD, 18));
        bottomPanel.add(inputField, BorderLayout.NORTH);
        bottomPanel.add(keypadPanel, BorderLayout.CENTER);
        bottomPanel.add(cancelBtn, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(screenPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i <= 9; i++) {
            if (e.getSource() == numButtons[i]) {
                inputField.setText(inputField.getText() + i);
            }
        }
        if (e.getSource() == clearBtn) {
            inputField.setText("");
        }
        if (!pinVerified) {
            if (e.getSource() == enterBtn) {
                if (inputField.getText().equals(correctPIN)) {
                    pinVerified = true;
                    screenLabel.setText("Welcome! Select a transaction");
                    JOptionPane.showMessageDialog(this, "PIN verified successfully!");
                    inputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect PIN! Try again.");
                    inputField.setText("");
                }
            }
        } else {
            if (e.getSource() == withdrawBtn) {
                currentAction = "withdraw";
                screenLabel.setText("Enter amount to withdraw");
            }
            if (e.getSource() == depositBtn) {
                currentAction = "deposit";
                screenLabel.setText("Enter amount to deposit");
            }
            if (e.getSource() == balanceBtn) {
                JOptionPane.showMessageDialog(this, "Your balance: ₹" + account.getBalance());
            }
            if (e.getSource() == enterBtn && !inputField.getText().isEmpty() && !currentAction.isEmpty()) {
                double amount = Double.parseDouble(inputField.getText());
                if (currentAction.equals("withdraw")) {
                    account.withdraw(amount);
                } else if (currentAction.equals("deposit")) {
                    account.deposit(amount);
                }
                inputField.setText("");
                currentAction = "";
                screenLabel.setText("Transaction completed!");
            }
        }
        if (e.getSource() == cancelBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(10000.0);
        new ATMInterface(account);
    }
}
