# ⏰ Java Alarm Clock

A simple console-based Alarm Clock built in Java that lets you set a time, shows a live ticking clock, and plays an alarm sound.

## 🔧 Features
- Set custom alarm time
- Console displays ticking clock (HH:MM:SS)
- Plays `.wav` audio at alarm time
- Press Enter to stop the alarm

## 🚀 How to Run
1. Copy the Java code into your compiler (e.g., VS Code or IntelliJ)
2. Place a `.wav` file in your project folder
3. Pass the alarm time and file path to the constructor:
   ```java
   LocalTime alarm = LocalTime.of(6, 30); // for 6:30 AM
   String filepath = "alarm.wav";
   Scanner scanner = new Scanner(System.in);
   new Thread(new Alarmclock1(alarm, filepath, scanner)).start();
