package org.bonn.se.ws14.uebung3;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by NilsK on 06.11.2015.
 */
class PriorityCalculator {
    public static void main(String[] args) {
        UserInputHelper uih = new UserInputHelper();

        while(true) {
            try {
                uih.getUserInput();
            } catch (org.bonn.se.ws14.uebung3.exceptions.NotAFibonacciNumberException nafne) {
                System.out.println("Angegebener Aufwand ist keine Fibonacci Zahl.");
            } catch (org.bonn.se.ws14.uebung3.exceptions.ContainerFullException cfe) {
                System.out.println("Es können maximal 10 UserStories angegeben werden.");
            } catch (org.bonn.se.ws14.uebung3.exceptions.PriorityCalculatorException pce) {
                System.out.println(pce.getMessage());
            } catch (org.bonn.se.ws14.uebung3.exceptions.UserQuit uq) {
                System.exit(0);
            } catch (NotImplementedException nie) {
                System.out.println("Dieses Feature wurde noch nicht implementiert.");
            }
        }

    }
}
