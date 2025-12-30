import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime alarmTime = null;
        String filepath = "C:\\Users\\prateek\\OneDrive\\Documents\\JAVA\\projects\\project 2\\music1.wav";
        

        while (alarmTime == null) {
            try {
                System.out.println("Give an alarm time (HH:mm:ss): ");
                String inputTime = scanner.nextLine();

                alarmTime = LocalTime.parse(inputTime, formatter);
                System.out.println("Alarm set for " + alarmTime);

            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Try again.");
            }
        }

       Alarmclock1 alarmClock = new Alarmclock1(alarmTime,filepath,scanner);
       Thread alarmThread = new Thread(alarmClock);
       alarmThread.start();
       
    }
}
