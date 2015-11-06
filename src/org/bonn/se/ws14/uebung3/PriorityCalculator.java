package org.bonn.se.ws14.uebung3;

import org.bonn.se.ws14.uebung3.exceptions.*;

/**
 * Created by NilsK on 06.11.2015.
 */
class PriorityCalculator {
    public static void main(String[] args) {
        UserInputHelper uih = new UserInputHelper();

        while(true) {
            try {
                uih.getUserInput();
            } catch (NotAFibonacciNumberException nafne) {
                System.out.println("Angegebener Aufwand ist keine Fibonacci Zahl.");
            } catch (ContainerFullException cfe) {
                System.out.println("Es können maximal 10 UserStories angegeben werden.");
            } catch (PriorityCalculatorException pce) {
                System.out.println(pce.getMessage());
            } catch (UserQuitException uq) {
                System.exit(0);
            }
        }

    }
}
