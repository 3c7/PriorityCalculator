package org.bonn.se.ws14.uebung3;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by NilsK on 17.10.2015.
 */
class Container implements Serializable {
    private List<UserStory> stories = new ArrayList<>();

    private static Container instance;

    private Container() {
    }

    void addStory(UserStory newStory) {
        this.stories.add(newStory);
    }

    void dump() {
        Collections.sort(this.stories);
        System.out.println("Priorität\tTitel\tMehrwert\tAufwand\tRisiko\tStrafe");
        for (UserStory us : this.stories) {
            System.out.println(new DecimalFormat("#0.00").format(us.getPriority()) + "\t\t\t" + us.getTitle() + "\t" + us.getValue() + "\t" + us.getEffort()
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
