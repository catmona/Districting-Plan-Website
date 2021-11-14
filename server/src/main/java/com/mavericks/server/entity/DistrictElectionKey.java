package com.mavericks.server.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class DistrictElectionKey implements Serializable {
    @Column(name = "districtId")
    private long districtId;

    @Column(name = "electionId")
    private long electionId;

    public DistrictElectionKey() {}
    public DistrictElectionKey(long districtId, long electionId) {
        this.districtId = districtId;
        this.electionId = electionId;
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
}