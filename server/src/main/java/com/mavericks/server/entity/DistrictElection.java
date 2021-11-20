package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "DistrictElections")
public class DistrictElection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtId", nullable=false)
    private long districtId;

    @Column(name="electionId", nullable=false)
    private long electionId;

    @Column(name="numberOfRepublicanVotes", nullable=false)
    private long republicanVotes;

    @Column(name="numberOfDemocraticVotes", nullable=false)
    private long democraticVotes;

    @Column(name="numberOfOtherVotes", nullable=false)
    private long otherVotes;

    public DistrictElection() {}

    public DistrictElection(long districtId, long electionId, long republicanVotes, long democraticVotes, long otherVotes) {
        this.districtId = districtId;
        this.electionId = electionId;
        this.republicanVotes = republicanVotes;
        this.democraticVotes = democraticVotes;
        this.otherVotes = otherVotes;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public long getElectionId() {
        return electionId;
    }

    public void setElectionId(long electionId) {
        this.electionId = electionId;
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

    public void setDemocraticVotes(long democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    public long getOtherVotes() {
        return otherVotes;
    }

    public void setOtherVotes(long otherVotes) {
        this.otherVotes = otherVotes;
    }
}
