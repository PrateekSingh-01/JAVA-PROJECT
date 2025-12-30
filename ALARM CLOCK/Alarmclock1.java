import java.time.LocalTime;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Alarmclock1 implements Runnable {

    private final LocalTime alarmTime;
    private final String filepath;
    private final Scanner scanner;
    Alarmclock1(LocalTime alarmTime,String filepath,Scanner scanner){
        this.alarmTime = alarmTime;
        this.filepath = filepath;
        this.scanner = scanner;

    }
    @Override
    public void run(){
        
        while(LocalTime.now().isBefore(alarmTime)){
            try{
                Thread.sleep(1000);

                LocalTime now = LocalTime.now(); 
              
                System.out.printf("\r%02d:%02d:%02d",now.getHour(), now.getMinute(),now.getSecond());



            }
            catch(InterruptedException e){
                System.out.println("Thread was interrupted");

            }
            

        }
        System.out.println("\nAlarm noises");
        playsound(filepath);

    }
    private void playsound(String filepath){
        File audiofile = new File(filepath);
 
       
        try( AudioInputStream audiostram =  AudioSystem.getAudioInputStream(audiofile)){
            Clip clip = AudioSystem.getClip();
            clip.open(audiostram);
            clip.start();
            System.out.println("press enter to stop the alarm: ");
            scanner.nextLine();
            clip.stop();
            scanner.close();
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("audio file not supported");
        }
        catch(LineUnavailableException e){
            System.out.println("audio is unable");

        } catch (IOException e) {
            
            System.out.println("io exception occured");
        }
        
        

    }
    
}
