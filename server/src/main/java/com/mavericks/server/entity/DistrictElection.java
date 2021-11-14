package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "DistrictElections")
public class DistrictElection {
    @EmbeddedId
    private DistrictElectionKey key;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electionId", insertable = false, updatable = false)
    private Election election;
    private long republicanVotes;
    private long democraticVotes;

    private long totalVotes;

    public DistrictElection(int republicanVotes, int democraticVotes, int totalVotes) {
        this.republicanVotes = republicanVotes;
        this.democraticVotes = democraticVotes;
        this.totalVotes = totalVotes;
    }

    public DistrictElectionKey getKey() {
        return key;
    }

    public void setKey(DistrictElectionKey key) {
        this.key = key;
    }

    public long getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(long republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public long getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocratVotes(long democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }
}
