package org.bonn.se.ws14.uebung4;

import org.bonn.se.ws14.uebung4.exceptions.*;

import java.io.IOException;

/**
 * Created by NilsK on 06.11.2015.
 */
class PriorityCalculator {
    public static void main(String[] args) {
        UserInputHelper uih = new UserInputHelper();

        // Loop getUserInput() until User wants to exit
        while(true) {
            try {
                uih.getUserInput();
            } catch (NotAFibonacciNumberException nafne) {
                System.out.println("Angegebener Aufwand ist keine Fibonacci Zahl.");
            } catch (ContainerFullException cfe) {
                System.out.println("Es können maximal 10 UserStories angegeben werden.");
            } catch (ValueOutOfRangeException voore) {
                System.out.println("Parameter außerhalb des zulässigen Bereiches.");
            } catch (PriorityCalculatorException pce) {
                System.out.println("Unerwarteter Fehler.");
            } catch (UserQuitException uq) {
                System.exit(0);
            } catch (IOException ioe) {
                System.out.println("Lesen von Konsoleneingaben nicht möglich.");
            }
        }

    }
}
