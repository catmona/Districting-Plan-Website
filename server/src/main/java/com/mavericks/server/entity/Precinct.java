package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.enumeration.Region;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Precincts")
public class Precinct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtId", nullable=false)
    private long districtId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "precinctId")
    private List<CensusBlock> censusBlocks;

    public Precinct() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public List<CensusBlock> getCensusBlocks() {
        return censusBlocks;
    }

    public void setCensusBlocks(List<CensusBlock> censusBlocks) {
        this.censusBlocks = censusBlocks;
    }
    public Region getRegion() {
        return Region.PRECINCT;
    }

}
