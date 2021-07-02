package com.example.mdproject;

public class LeaderboardProfiles {
    String name;
    String score;

    public LeaderboardProfiles() {}

    public LeaderboardProfiles(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
