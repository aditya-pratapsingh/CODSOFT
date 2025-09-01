import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessNumber extends JFrame implements ActionListener {
    private JTextField guessField;
    private JLabel messageLabel, attemptsLabel, scoreLabel;
    private JButton guessButton, newGameButton;
    private int numberToGuess, attempts, score;
    private final int MAX_ATTEMPTS = 7;
    private Random random = new Random();

    public GuessNumber() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Guess the Number!", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBackground(new Color(240, 248, 255));

        messageLabel = new JLabel("Enter a number between 1 and 100:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(0, 102, 204));

        guessField = new JTextField();
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("Arial", Font.BOLD, 18));
        guessField.setBackground(new Color(255, 255, 204));

        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(50, 205, 50));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.addActionListener(this);

        centerPanel.add(messageLabel);
        centerPanel.add(guessField);
        centerPanel.add(guessButton);

        attemptsLabel = new JLabel("Attempts Left: " + MAX_ATTEMPTS, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel.setForeground(new Color(128, 0, 128));
        centerPanel.add(attemptsLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.setBackground(new Color(240, 248, 255));

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(178, 34, 34));

        newGameButton = new JButton("New Game");
        newGameButton.setBackground(new Color(70, 130, 180));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        newGameButton.addActionListener(e -> resetGame());

        bottomPanel.add(scoreLabel);
        bottomPanel.add(newGameButton);
        add(bottomPanel, BorderLayout.SOUTH);

        startNewRound();
        setVisible(true);
    }

    private void startNewRound() {
        numberToGuess = random.nextInt(100) + 1;
        attempts = 0;
        messageLabel.setText("Enter a number between 1 and 100:");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        attemptsLabel.setText("Attempts Left: " + MAX_ATTEMPTS);
        getContentPane().setBackground(new Color(240, 248, 255));
    }

    private void resetGame() {
        score = 0;
        scoreLabel.setText("Score: " + score);
        startNewRound();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                int remaining = MAX_ATTEMPTS - attempts;

                if (guess == numberToGuess) {
                    messageLabel.setText("\u2705 Correct! The number was " + numberToGuess);
                    getContentPane().setBackground(new Color(144, 238, 144));
                    score += (remaining + 1) * 10;
                    scoreLabel.setText("Score: " + score);
                    guessButton.setEnabled(false);
                    guessField.setEditable(false);
                } else if (attempts >= MAX_ATTEMPTS) {
                    messageLabel.setText("\u274C Out of attempts! Number was " + numberToGuess);
                    getContentPane().setBackground(new Color(255, 99, 71));
                    guessButton.setEnabled(false);
                    guessField.setEditable(false);
                } else if (guess < numberToGuess) {
                    messageLabel.setText("\u2B07 Too low! Try again.");
                } else {
                    messageLabel.setText("\u2B06 Too high! Try again.");
                }
                attemptsLabel.setText("Attempts Left: " + remaining);
            } catch (NumberFormatException ex) {
                messageLabel.setText("\u26A0 Enter a valid number!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessNumber::new);
    }
}
