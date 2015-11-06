package org.bonn.se.ws14.uebung3;

import org.bonn.se.ws14.uebung3.exceptions.*;
import java.io.*;

/**
 * Created by NilsK on 06.11.2015.
 */
class UserInputHelper {
    private Container userStoryContainer = Container.getInstance();
    private String userInput;
    private boolean debug = false;

    // Methods
    public void getUserInput() throws PriorityCalculatorException, UserQuitException {
        String command;

        // Reads a new line from java.lang.System.in
        this.readline();

        // Get the command and check if there are any parameters
        if (!this.userInput.contains(" ")) {
            command = this.userInput;
        } else {
            // Remove command from userInput-String
            command = this.userInput.substring(0, this.userInput.indexOf(" "));
            this.userInput = this.userInput.substring(this.userInput.indexOf(" ") + 1);
        }

        // Check if command is in necessary commandlist
        switch (command) {
            case "enter":
                this.commandEnter();
                break;
            case "quit":
                this.commandQuit();
                break;
            case "dump":
                this.commandDump();
                break;
            case "load":
                this.commandLoad();
                break;
            case "store":
                this.commandStore();
                break;
            case "debug":
                this.debug = true;
                System.out.println("Erweiterte Ausgabe angeschaltet.");
                break;
            case "help":
            default:
                this.commandHelp();
        }

        // Clear user input
        this.userInput = "";

    }

    private void readline() {
        // Create BufferedReader to read from System.in
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

        // Cursor
        System.out.print("> ");
        try {
            this.userInput = bReader.readLine();
        } catch (IOException ioe) {
            System.out.println("Fehler: BufferedReader konnte nicht von System.in lesen.");
            ioe.printStackTrace();
        }
    }

    // ENTER
    private void commandEnter() throws PriorityCalculatorException {
        if (userStoryContainer.getCount() >= 10) throw new ContainerFullException();

        // Variables
        int value, effort, risk, penalty;

        // Split the parameters
        String data[] = this.userInput.split(",");


        // Create the org.bonn.se.ws14.uebung3.UserStory
        UserStory uStory = new UserStory();

        // Fill necessary information
        try {
            value = Integer.parseInt(data[1]);
            effort = Integer.parseInt(data[2]);
            risk = Integer.parseInt(data[3]);
            penalty = Integer.parseInt(data[4]);

            // Check if parameter are in correct range
            if (!inRange(value, 0, 5) || !inRange(risk, 0, 5) || !inRange(penalty, 0, 5))
                throw new ValueOutOfRangeException();

            // Check if Effort is a Fibonacci Number
            if (!fibonacciCheck(effort))
                throw new NotAFibonacciNumberException();

            uStory.setTitle(data[0]);
            uStory.setValue(value);
            uStory.setEffort(effort);
            uStory.setRisk(risk);
            uStory.setPenalty(penalty);
            uStory.setPriority((double)(value + penalty) / (effort + risk));

            // Append UserStory to container
            userStoryContainer.addStory(uStory);

            // Printout message for the user
            System.out.println("Story \"" + uStory.getTitle() + "\" hinzugefügt.");
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("FEHLER: Erforderlichen Parameter nicht eingegeben oder falsch formatiert.");
            this.commandHelp();
        }
    }

    // HELP
    // Prints the "usage"
    private void commandHelp() {
        System.out.println("Hilfe:");
        System.out.println(">_ Befehl [parameter]\n" +
                "Mögliche Befehle:\n" +
                "enter titel,V,A,R,S\t-\tEine neue Userstory eingeben; V->Value, R->Risiko, S->Strafe als Ganzzahlen [1-5]; A->Aufwand als Fibonacci Zahl\n" +
                "load file\t\t\t-\tUserstories mit dem Dateinamen file laden\n" +
                "save file\t\t\t-\tUserstories mit dem Dateinamen file speichern\n" +
                "help\t\t\t\t-\tZeigt diese Hilfe an\n" +
                "quit\t\t\t\t-\tBeendet das Programm");
    }

    // QUIT
    // For exiting the program
    private void commandQuit() throws UserQuitException {
        throw new UserQuitException();
    }

    // DUMP
    // Dump container
    private void commandDump() {
        this.userStoryContainer.dump();
    }

    // STORE
    // Save the current container into a *.priocalc-file, the user has not to append .priocalc
    private void commandStore() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.userInput + ".priocalc", false));
            outputStream.writeObject(this.userStoryContainer);
        } catch (FileNotFoundException fnfe) {
            System.out.println("FEHLER: Konnte Datei nicht öffnen.");
            this.debugOut(fnfe);
        } catch (IOException ioe) {
            System.out.println("FEHLER: Konnte nicht in Datei schreiben.");
            this.debugOut(ioe);
        }
    }

    // LOAD
    // Load a container from a *.priocalc-file, the user has not to appent .priocalc
    private void commandLoad() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.userInput + ".priocalc"));
            userStoryContainer = (Container)inputStream.readObject();
        } catch (FileNotFoundException fnfe) {
            System.out.println("FEHLER: Datei nicht gefunden.");
            this.debugOut(fnfe);
        } catch (IOException ioe) {
            System.out.println("FEHLER: Konnte nicht aus Datei lesen");
            this.debugOut(ioe);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("FEHLER: Datei gefunden, aber der Inhalt ist nicht kompatibel.");
            this.debugOut(cnfe);
        }
    }

    // Simple check if Effort is a fibonacci numer
    private static boolean fibonacciCheck(int toCheck) {
        int fibonacciNumbers[] = {0, 1, 2, 3, 5, 8, 13, 20, 40, 100};
        boolean result = false;
        for (int n : fibonacciNumbers) {
            if (toCheck == n) result = true;
        }
        return result;
    }

    // Get StackTraces in debug-mode
    private void debugOut(Exception e) {
        if(this.debug) e.printStackTrace();
    }

    private static boolean inRange(int a, int b, int c) {
        return a>=b && a<=c;
    }
}
