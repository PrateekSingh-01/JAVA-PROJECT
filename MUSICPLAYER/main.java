import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class main {
   public static void main(String[] args) {

    String filePath = "moonlight.wav";/*drag and drop your song in wav format in the folder and copy the path past it in filepath and use
       this sample code*/
    File file = new File(filePath);

    try(Scanner scanner = new Scanner(System.in);AudioInputStream audiostream = AudioSystem.getAudioInputStream(file)){
       
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        
        String response = "";
        while (!response.equals("Q")) {
            System.out.println("P=play");
            System.out.println("S=stop");
            System.out.println("R=reset");
            System.out.println("Q=quit");
            System.out.println("Enter your choise: ");

            response = scanner.next().toUpperCase();

            switch (response) {
                case "P" -> clip.start();
                case "S" -> clip.stop();
                case "R" -> clip.setMicrosecondPosition(0);
                case "Q" -> clip.close();
                default -> System.out.println("invalid choise");
                    
            }
            
        }

        System.out.println("no problem detected");
    }
    catch(FileNotFoundException e){
        System.out.println("file could not locate");
    }
    catch(UnsupportedAudioFileException e) {
        System.out.println("Audio file not supported"); 

    }  
    catch(LineUnavailableException e){
        System.out.println("Unable to access resource");

    }
    catch(IOException e){
        System.out.println("Someting went wrong");
    }
    finally{
        System.out.println("bye");
        
    }
    }
    
}
