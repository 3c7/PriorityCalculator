package org.bonn.se.ws14.uebung3;

import java.io.Serializable;

/**
 * Created by NilsK on 06.11.2015.
 */
class UserStory implements Comparable<UserStory>, Serializable {
    private int value, effort, risk, penalty;
    private double priority;
    private String title;

    // Constructors

    public UserStory() {
    }

    public UserStory(String t, int v, int e, int r, int pe) {
        this.title = t;
        this.value = v;
        this.effort = e;
        this.risk = r;
        this.penalty = pe;
    }

    public UserStory(String t, int v, int e, int r, int pe, int pr) {
        this.title = t;
        this.value = v;
        this.effort = e;
        this.risk = r;
        this.penalty = pe;
        this.priority = pr;
    }

    // Methods

    public int compareTo(UserStory us) {
        if (this.priority > us.priority) return -1;
        if (this.priority < us.priority) return 1;
        return 0;
    }

    // Getter & Setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}