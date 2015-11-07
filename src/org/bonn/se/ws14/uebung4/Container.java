package org.bonn.se.ws14.uebung4;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by NilsK on 17.10.2015.
 */
class Container implements Serializable {
    private final List<UserStory> stories = new ArrayList<>();

    private static Container instance;

    private Container() {
    }

    void addStory(UserStory newStory) {
        this.stories.add(newStory);
    }

    void dump() {
        Collections.sort(this.stories);
        System.out.println("Priorität\tTitel\tMehrwert\tAufwand\tRisiko\tStrafe");

        this.stories.forEach(System.out::println);
    }

    int getCount() {
        return this.stories.size();
    }

    static Container getInstance() {
        synchronized (Container.class) {
            if (instance == null)
                instance = new Container();
        }
        return instance;
    }
}
