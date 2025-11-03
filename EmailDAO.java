import java.io.*;
import java.util.*;

class EmailDAO {
    private static final String FILE_NAME = "emails_data.txt";

    public void saveEmail(Email email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(email.getTimestamp() + "|" + email.getFrom() + "|" + email.getTo() + "|" + email.getSubject() + "|" + email.getBody() + "|" + email.getStatus());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving email: " + e.getMessage());
        }
    }

    public List<Email> getAllEmails() {
        List<Email> emails = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return emails;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    Email email = new Email(parts[1], parts[2], parts[3], parts[4]);
                    email.setStatus(parts[5]);
                    emails.add(email);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading emails: " + e.getMessage());
        }
        return emails;
    }

    public void clearHistory() {
        try {
            new FileWriter(FILE_NAME, false).close();
        } catch (IOException e) {
            System.err.println("Error clearing history: " + e.getMessage());
        }
    }
}
