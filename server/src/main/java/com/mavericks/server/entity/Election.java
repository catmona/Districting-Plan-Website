package com.mavericks.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateId")
    private State state;
    @Enumerated(EnumType.STRING)
    private ElectionType electionTypeEnum;
    @OneToMany(mappedBy = "election", fetch = FetchType.LAZY)
    private List<DistrictElection> districtElections;

    @Transient
    private int totalDemocratVotes;
    @Transient
    private int totalRepublicanVotes;
    @Transient
    private int totalVotes;

    public Election(int totalDemocratVotes, int totalRepublicanVotes) {
        this.totalDemocratVotes = totalDemocratVotes;
        this.totalRepublicanVotes = totalRepublicanVotes;
        this.totalVotes = totalDemocratVotes+totalRepublicanVotes;
    }

    public DistrictElection getVoteByDistrict(int distNum){
        return districtElections.get(distNum);
    }

    public int getTotalDemocratVotes() {
        int votes = 0;
        for(DistrictElection dv : districtElections) {
            votes += dv.getDemocraticVotes();
        }
        return votes;
    }

    public void setTotalDemocratVotes(int totalDemocratVotes) {
        this.totalDemocratVotes = totalDemocratVotes;
    }

    public int getTotalRepublicanVotes() {
        int votes = 0;
        for(DistrictElection dv : districtElections) {
            votes += dv.getRepublicanVotes();
        }
        return votes;
    }

    public void setTotalRepublicanVotes(int totalRepublicanVotes) {
        this.totalRepublicanVotes = totalRepublicanVotes;
    }

    public int getTotalVotes() {
        int votes = 0;
        for(DistrictElection dv : districtElections) {
            votes += dv.getTotalVotes();
        }
        return votes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<DistrictElection> getDistrictElections() {
        return districtElections;
    }

    public void setDistrictElections(List<DistrictElection> districtElections) {
        this.districtElections = districtElections;
    }
}
