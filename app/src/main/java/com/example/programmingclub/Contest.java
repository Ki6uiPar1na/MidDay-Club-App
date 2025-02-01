package com.example.programmingclub;

public class Contest {
    private String contestName;
    private int rank;
    private int oldRating;
    private int newRating;

    public Contest(String contestName, int rank, int oldRating, int newRating) {
        this.contestName = contestName;
        this.rank = rank;
        this.oldRating = oldRating;
        this.newRating = newRating;
    }

    public String getContestName() {
        return contestName;
    }

    public int getRank() {
        return rank;
    }

    public int getOldRating() {
        return oldRating;
    }

    public int getNewRating() {
        return newRating;
    }
}
