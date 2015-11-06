package org.bonn.se.ws14.uebung3;

import org.bonn.se.ws14.uebung3.exceptions.NotAFibonacciNumberException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

/**
 * Created by NilsK on 06.11.2015.
 */
class UserInputHelper {
    private BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
    private Container userStoryContainer = Container.getInstance();
    private String temp;
    private boolean debug = false;

    // Methods
    public void getUserInput() throws org.bonn.se.ws14.uebung3.exceptions.PriorityCalculatorException, org.bonn.se.ws14.uebung3.exceptions.UserQuit {
        String command;

        readline();

        // Get the command
        // Check if command has parameters
        if (!this.temp.contains(" ")) {
            command = this.temp;
        } else {
            // Remove command from temp-String
            command = this.temp.substring(0, this.temp.indexOf(" "));
            this.temp = this.temp.substring(this.temp.indexOf(" ") + 1);
        }

        switch (command) {
            case "help":
                this.commandHelp();
                break;
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
            default:
                throw new NotImplementedException();
        }
        this.temp = "";

    }

    private void readline() {
        System.out.print(">_ ");
        try {
            this.temp = bReader.readLine();
        } catch (IOException ioe) {
            System.out.println("Fehler: BufferedReader konnte nicht von System.in lesen. (" + ioe.getMessage() + ")");
        }
    }

    private void commandEnter() throws org.bonn.se.ws14.uebung3.exceptions.PriorityCalculatorException {
        if (userStoryContainer.getCount() >= 10) throw new org.bonn.se.ws14.uebung3.exceptions.ContainerFullException();

        // Split the parameters
        String data[] = this.temp.split(",");


        // Create the org.bonn.se.ws14.uebung3.UserStory
        UserStory uStory = new UserStory();

        // Fill necessary information
        try {
            uStory.setTitle(data[0]);
            uStory.setValue(Integer.parseInt(data[1]));
            uStory.setEffort(Integer.parseInt(data[2]));
            uStory.setRisk(Integer.parseInt(data[3]));
            uStory.setPenalty(Integer.parseInt(data[4]));
            uStory.setPriority((double)(uStory.getValue() + uStory.getPenalty()) / (uStory.getEffort() + uStory.getRisk()));
            // Check if Effort is a Fibonacci Number
            if (!fibonacciCheck(uStory.getEffort()))
                throw new NotAFibonacciNumberException();

            userStoryContainer.addStory(uStory);
            System.out.println("Story \"" + uStory.getTitle() + "\" hinzugefügt.");
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("FEHLER: Erforderlichen Parameter nicht eingegeben oder falsch formatiert.");
            this.commandHelp();
        }
    }

    private void commandHelp() {
        System.out.println("Hilfe:");
        System.out.println(">_ Befehl [parameter]\n" +
                "Mögliche Befehle:\n" +
                "enter titel,V,A,R,S\t-\tEine neue Userstory eingeben; V->Value, R->Risiko, S->Strafe als Ganzzahle; A->Aufwand als Fibonacci Zahl\n" +
                "load file\t\t\t-\tUserstories mit dem Dateinamen file laden\n" +
                "save file\t\t\t-\tUserstories mit dem Dateinamen file speichern\n" +
                "help\t\t\t\t-\tZeigt diese Hilfe an\n" +
                "quit\t\t\t\t-\tBeendet das Programm");
    }

    private void commandQuit() throws org.bonn.se.ws14.uebung3.exceptions.UserQuit {
        throw new org.bonn.se.ws14.uebung3.exceptions.UserQuit();
    }

    private void commandDump() {
        this.userStoryContainer.dump();
    }

    private void commandStore() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.temp + ".priocalc", false));
            outputStream.writeObject(this.userStoryContainer);
        } catch (FileNotFoundException fnfe) {
            System.out.println("FEHLER: Konnte Datei nicht öffnen.");
            this.debugOut(fnfe);
        } catch (IOException ioe) {
            System.out.println("FEHLER: Konnte nicht in Datei schreiben.");
            this.debugOut(ioe);
        }
    }

    private void commandLoad() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.temp + ".priocalc"));
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

    private static boolean fibonacciCheck(int toCheck) {
        int fibonacciNumbers[] = {0, 1, 2, 3, 5, 8, 13, 20, 40, 100};
        boolean result = false;
        for (int n : fibonacciNumbers) {
            if (toCheck == n) result = true;
        }
        return result;
    }

    /*private void debugOut(String str) {
        if(this.debug) System.out.println(str);
    }*/

    private void debugOut(Exception e) {
        if(this.debug) e.printStackTrace();
    }
}
