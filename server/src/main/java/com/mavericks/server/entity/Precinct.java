package com.mavericks.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Precincts")
public class Precinct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private District district;
    @OneToMany(mappedBy = "precinct", fetch = FetchType.LAZY)
    private List<CensusBlock> censusBlocks;

    @Transient
    private Population population;

    public Precinct() {}

    public Precinct(long id, District district) {
        this.id = id;
        this.district = district;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Population getPopulation() {
        // get population by aggregating the plan's District populations
        Population res = new Population();
        for (CensusBlock cb: censusBlocks) {
            res.combinePopulations(cb.getPopulation());
        }
        return res;
    }
}
