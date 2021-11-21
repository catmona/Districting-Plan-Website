package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "DistrictElections")
public class DistrictElection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "districtId", length = 50, nullable = false)
    private String districtId;

    @Column(name = "electionId", length = 50, nullable = false)
    private String electionId;

    @Column(name = "numberOfRepublicanVotes", nullable = false)
    private long republicanVotes;

    @Column(name = "numberOfDemocraticVotes", nullable = false)
    private long democraticVotes;

    @Column(name = "numberOfOtherVotes", nullable = false)
    private long otherVotes;

    public DistrictElection() {}

    public DistrictElection(String districtId, String electionId, long republicanVotes, long democraticVotes, long otherVotes) {
        this.districtId = districtId;
        this.electionId = electionId;
        this.republicanVotes = republicanVotes;
        this.democraticVotes = democraticVotes;
        this.otherVotes = otherVotes;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
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
