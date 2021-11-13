package com.mavericks.server.entity;

public class DistrictVoting {

    private int republicanVotes;
    private int democratVotes;
    private int totalVotes;

    public DistrictVoting(int republicanVotes, int democratVotes, int totalVotes) {
        this.republicanVotes = republicanVotes;
        this.democratVotes = democratVotes;
        this.totalVotes = totalVotes;
    }

    public int getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(int republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public int getDemocratVotes() {
        return democratVotes;
    }

    public void setDemocratVotes(int democratVotes) {
        this.democratVotes = democratVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
