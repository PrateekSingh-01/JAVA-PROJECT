import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class EmailGUI extends JFrame {
    private JTextField toField, subjectField, fromField;
    private JTextArea bodyArea;
    private JButton sendButton, historyButton, clearButton, configButton;
    private EmailService emailService;
    private JLabel statusLabel;

    public EmailGUI() {
        emailService = new EmailService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Email Sending Application - Pure Java");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        configPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)), "Email Configuration", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        configPanel.setBackground(new Color(240, 248, 255));

        configPanel.add(new JLabel("Your Email:"));
        fromField = new JTextField(25);
        configPanel.add(fromField);

    configButton = new JButton("Configure");
    configButton.setBackground(new Color(70, 130, 180));
    configButton.setForeground(Color.WHITE);
    configButton.setFocusPainted(false);
    configButton.setOpaque(true);
    configButton.setBorderPainted(false);
    configButton.setFont(configButton.getFont().deriveFont(Font.BOLD));
    configButton.addActionListener(e -> configureEmail());
    configPanel.add(configButton);

        JPanel emailPanel = new JPanel(new GridBagLayout());
        emailPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)), "Compose Email", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        emailPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0; gbc.gridy = 0;
    emailPanel.add(new JLabel("To:"), gbc);
    gbc.gridx = 1; gbc.weightx = 1.0; toField = new JTextField(30); emailPanel.add(toField, gbc);

    gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
    emailPanel.add(new JLabel("Subject:"), gbc);
    gbc.gridx = 1; gbc.weightx = 1.0; subjectField = new JTextField(30); emailPanel.add(subjectField, gbc);

    gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
    emailPanel.add(new JLabel("Body:"), gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1; gbc.weightx = 1.0;
    bodyArea = new JTextArea(12, 30);
    bodyArea.setLineWrap(true);
    bodyArea.setWrapStyleWord(true);
        JScrollPane bodyScroll = new JScrollPane(bodyArea);
        emailPanel.add(bodyScroll, gbc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

    sendButton = new JButton("\uD83D\uDCE7 Send Email");
    sendButton.setBackground(new Color(76, 175, 80));
    sendButton.setForeground(Color.WHITE);
    sendButton.setOpaque(true);
    sendButton.setBorderPainted(false);
    sendButton.setFocusPainted(false);
    sendButton.setFont(sendButton.getFont().deriveFont(Font.BOLD));
    sendButton.addActionListener(e -> sendEmail());

    historyButton = new JButton("\uD83D\uDCCB View History");
    historyButton.setBackground(new Color(33, 150, 243));
    historyButton.setForeground(Color.WHITE);
    historyButton.setOpaque(true);
    historyButton.setBorderPainted(false);
    historyButton.setFocusPainted(false);
    historyButton.setFont(historyButton.getFont().deriveFont(Font.BOLD));
    historyButton.addActionListener(e -> viewHistory());

    clearButton = new JButton("\uD83D\uDDD1\uFE0F Clear History");
    clearButton.setBackground(new Color(244, 67, 54));
    clearButton.setForeground(Color.WHITE);
    clearButton.setOpaque(true);
    clearButton.setBorderPainted(false);
    clearButton.setFocusPainted(false);
    clearButton.setFont(clearButton.getFont().deriveFont(Font.BOLD));
    clearButton.addActionListener(e -> clearHistory());

        buttonPanel.add(sendButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(clearButton);

        statusLabel = new JLabel("Ready to send emails", SwingConstants.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(configPanel, BorderLayout.NORTH);
        mainPanel.add(emailPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void configureEmail() {
        String from = fromField.getText().trim();
        if (from.isEmpty() || !isValidEmail(from)) {
            JOptionPane.showMessageDialog(this, "Enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        emailService.configure(from);
        statusLabel.setText("✓ Email configured: " + from);
        statusLabel.setForeground(new Color(76, 175, 80));
        JOptionPane.showMessageDialog(this, "Email configured successfully!\n\nNote: Simulation only.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void sendEmail() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String subject = subjectField.getText().trim();
        String body = bodyArea.getText().trim();

        if (from.isEmpty() || to.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidEmail(to)) {
            JOptionPane.showMessageDialog(this, "Invalid recipient email!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Email email = new Email(from, to, subject, body);
        JDialog dialog = new JDialog(this, "Sending Email", true);
        dialog.setSize(350, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("Sending email, please wait...", SwingConstants.CENTER), BorderLayout.CENTER);
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);
        dialog.add(bar, BorderLayout.SOUTH);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            protected Boolean doInBackground() {
                return emailService.sendEmail(email);
            }
            protected void done() {
                dialog.dispose();
                try {
                    if (get()) {
                        statusLabel.setText("✓ Email sent successfully!");
                        statusLabel.setForeground(new Color(76, 175, 80));
                        JOptionPane.showMessageDialog(EmailGUI.this, "Email sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        toField.setText(""); subjectField.setText(""); bodyArea.setText("");
                    } else {
                        statusLabel.setText("✗ Failed to send email");
                        statusLabel.setForeground(Color.RED);
                    }
                } catch (Exception ex) {
                    statusLabel.setText("✗ Error");
                    statusLabel.setForeground(Color.RED);
                }
            }
        };
        worker.execute();
        dialog.setVisible(true);
    }

    private void viewHistory() {
        JDialog dialog = new JDialog(this, "Email History", true);
        dialog.setSize(700, 450);
        dialog.setLayout(new BorderLayout());
        List<Email> emails = emailService.getEmailHistory();
        JTextArea area = new JTextArea();
        area.setEditable(false);
        if (emails.isEmpty()) area.setText("No emails sent yet.");
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < emails.size(); i++) {
                Email e = emails.get(i);
                sb.append("Email #").append(i + 1).append("\n");
                sb.append("From: ").append(e.getFrom()).append("\nTo: ").append(e.getTo()).append("\nSubject: ").append(e.getSubject()).append("\nTime: ").append(e.getTimestamp()).append("\nStatus: ").append(e.getStatus()).append("\n\n");
            }
            area.setText(sb.toString());
        }
        dialog.add(new JScrollPane(area), BorderLayout.CENTER);
        JButton close = new JButton("Close");
        close.addActionListener(e -> dialog.dispose());
        JPanel panel = new JPanel();
        panel.add(close);
        dialog.add(panel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void clearHistory() {
        if (JOptionPane.showConfirmDialog(this, "Clear all history?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            emailService.clearHistory();
            statusLabel.setText("✓ History cleared");
            statusLabel.setForeground(new Color(255, 152, 0));
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new EmailGUI().setVisible(true));
    }
}
