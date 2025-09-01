import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class gradecalc extends JFrame implements ActionListener {
    private JTextField[] marksField;
    private JButton calcButton;
    private JTextArea resultArea;

    public gradecalc(int numSubjects) {
        setTitle("Student Grade Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(numSubjects + 1, 2, 10, 10));
        marksField = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            inputPanel.add(new JLabel("Marks for Subject " + (i + 1) + ":"));
            marksField[i] = new JTextField();
            inputPanel.add(marksField[i]); 
        }

        calcButton = new JButton("Calculate Result");
        calcButton.addActionListener(this);
        inputPanel.add(calcButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int total = 0;
            int n = marksField.length;

            for (int i = 0; i < n; i++) {
                int mark = Integer.parseInt(marksField[i].getText());
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Enter marks between 0 and 100 only!");
                    return;
                }
                total += mark;
            }

            double average = (double) total / n;
            char grade;
            if (average >= 90) grade = 'A';
            else if (average >= 75) grade = 'B';
            else if (average >= 60) grade = 'C';
            else if (average >= 40) grade = 'D';
            else grade = 'F';

            resultArea.setText("---- Result ----\n");
            resultArea.append("Total Marks: " + total + "/" + (n * 100) + "\n");
            resultArea.append("Average Percentage: " + String.format("%.2f", average) + "%\n");
            resultArea.append("Grade: " + grade + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for all subjects!");
        }
    }

    public static void main(String[] args) {
        int numSubjects = 5;
        new gradecalc(numSubjects).setVisible(true);
    }
}
