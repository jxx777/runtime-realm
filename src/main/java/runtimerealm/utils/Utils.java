package runtimerealm.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static Random randomlyGenerated = new Random();
    public static Scanner getInput = new Scanner(System.in);

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            getConsolePrompt("Gameplay was interrupted.");
        }
    }

    public static String getPlayerChoice() {
        return getInput.nextLine().trim().toLowerCase();
    }

    public static String getConsoleInput(String message) {
        System.out.print(message);
        return getInput.nextLine().trim();
    }

    public static void getConsolePrompt(String message) {
        System.out.println(message);
    }

    public static int rollDice(int origin, int bound) {
        return randomlyGenerated.nextInt(origin, bound);
    }

    /**
     * Logs the details of a caught exception to a file. The logged details include the exception's class, message,
     * and stack trace. This method is useful for recording exceptions for later analysis and debugging.
     *
     * @param passedException The exception to log. This should be the exception caught in a catch block.
     */
    public static void logException(Exception passedException) {
        writeErrorLogToFile(
                passedException.getClass() // The class that threw the exception
                        + " "
                        + Arrays.toString(passedException.getStackTrace()) // The full stack trace
                        + " MESSAGE: "
                        + passedException.getMessage() // The exception message
        ); // Log the error if caught
    }

    /**
     * Writes a given log message to a file named 'error_log.txt'. Each log entry is timestamped
     * using the format 'yyyy/MM/dd HH:mm:ss'. This method is designed to append log messages to the
     * file, ensuring that previous log entries are preserved.
     *
     * @param message The log message to be written. This typically includes details about an exception
     *                or error that has occurred.
     */
    private static void writeErrorLogToFile(String message) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // Date format for timestamp
        String timestamp = dateTimeFormatter.format(LocalDateTime.now()); // Initialize timestamp on method call
        String logMessage = timestamp + " - " + message + System.lineSeparator(); // Concatenate string (timestamp + error message + '\n')

        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter("error_log.txt", true))) { // in the file error_log.txt, adding a new line for each entry
            logWriter.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace(); // In case the logging itself fails
        }
    }

    public static void clearScreen() {
        System.out.print("\u001B[2J");
        System.out.flush();
        System.out.print("\u001B[H");
        System.out.flush();
    }
}