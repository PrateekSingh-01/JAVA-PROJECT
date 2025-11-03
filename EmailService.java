import java.util.List;

class EmailService {
    private EmailDAO emailDAO;
    private String configuredEmail;

    public EmailService() {
        this.emailDAO = new EmailDAO();
    }

    public void configure(String email) {
        this.configuredEmail = email;
    }

    public boolean sendEmail(Email email) {
        try {
            Thread.sleep(1000);
            emailDAO.saveEmail(email);
            email.setStatus("Sent Successfully");
            return true;
        } catch (Exception e) {
            email.setStatus("Failed: " + e.getMessage());
            return false;
        }
    }

    public List<Email> getEmailHistory() {
        return emailDAO.getAllEmails();
    }

    public void clearHistory() {
        emailDAO.clearHistory();
    }
}
