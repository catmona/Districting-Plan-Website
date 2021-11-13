package com.mavericks.server.entity;

import java.util.List;

public class Election {

    private int totalDemocratVotes;
    private int toatlRepublicanVotes;
    private int totalVotes;
    private List<DistrictVoting> districtVotes;

    public Election(int totalDemocratVotes, int toatlRepublicanVotes, int totalVotes, List<DistrictVoting> districtVotes) {
        this.totalDemocratVotes = totalDemocratVotes;
        this.toatlRepublicanVotes = toatlRepublicanVotes;
        this.totalVotes = totalVotes;
        this.districtVotes = districtVotes;
    }

    public DistrictVoting getVoteByDistrict(int distNum){
        return districtVotes.get(distNum);
    }

    public int getTotalDemocratVotes() {
        return totalDemocratVotes;
    }

    public void setTotalDemocratVotes(int totalDemocratVotes) {
        this.totalDemocratVotes = totalDemocratVotes;
    }

    public int getToatlRepublicanVotes() {
        return toatlRepublicanVotes;
    }

    public void setToatlRepublicanVotes(int toatlRepublicanVotes) {
        this.toatlRepublicanVotes = toatlRepublicanVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<DistrictVoting> getDistrictVotes() {
        return districtVotes;
    }

    public void setDistrictVotes(List<DistrictVoting> districtVotes) {
        this.districtVotes = districtVotes;
    }
}
