package org.bonn.se.ws14.uebung3;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by NilsK on 17.10.2015.
 */
class Container implements Serializable {
    private List<UserStory> stories = new ArrayList<>();

    // Change Request 2
    private static Container instance;

    // Change Request 1
    // Nutzen des Konstruktors verhindern
    private Container() {
    }
    /*
    private org.bonn.se.ws14.uebung3.Container(Member newMember) throws ContainerException {
        this.addStory(newMember);
    }
    */

    void addStory(UserStory newStory) {
        this.stories.add(newStory);
    }

// --Commented out by Inspection START (06.11.2015 20:26):
//    String removeStory(String title) {
//        Iterator<org.bonn.se.ws14.uebung3.UserStory> iterator = this.stories.iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next().getTitle().equals(title)) {
//                iterator.remove();
//                return "org.bonn.se.ws14.uebung3.UserStory mit dem Titel " + title + " wurde gelöscht.";
//            }
//        }
//        return "org.bonn.se.ws14.uebung3.UserStory mit dem Titel" + title + " wurde nicht gefunden.";
//    }
// --Commented out by Inspection STOP (06.11.2015 20:26)

    void dump() {
        Collections.sort(this.stories);
        System.out.println("Priorität\tTitel\tMehrwert\tAufwand\tRisiko\tStrafe");
        for (UserStory us : this.stories) {
            System.out.println(new DecimalFormat("##.##").format(us.getPriority()) + "\t\t\t" + us.getTitle() + "\t" + us.getValue() + "\t" + us.getEffort()
                    + "\t" + us.getRisk() + "\t" + us.getPenalty());
        }
    }

    int getCount() {
        return this.stories.size();
    }

    // Change Request 2
    static Container getInstance() {
        synchronized (Container.class) {
            if (instance == null)
                instance = new Container();
        }
        return instance;
    }
}
