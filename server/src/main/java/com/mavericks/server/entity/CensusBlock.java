package com.mavericks.server.entity;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CensusBlocks")
public class CensusBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "precinctId")
    private Precinct precinct;
    private Geometry geometry;
    private boolean isBorderBlock;

    @OneToMany(mappedBy = "censusBlock", fetch = FetchType.LAZY)
    private List<CensusBlockPopulation> censusBlockPopulations;

    @Transient
    private Population population;

    public CensusBlock() {}

    public CensusBlock(long id, Precinct precinct, Geometry geometry, boolean isBorderBlock) {
        this.id = id;
        this.precinct = precinct;
        this.geometry = geometry;
        this.isBorderBlock = isBorderBlock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Precinct getPrecinct() {
        return precinct;
    }

    public void setPrecinct(Precinct precinct) {
        this.precinct = precinct;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isBorderBlock() {
        return isBorderBlock;
    }

    public void setBorderBlock(boolean borderBlock) {
        isBorderBlock = borderBlock;
    }

    public Population getPopulation() {
        // get population by aggregating the populations
        Population res = new Population();
        return res;
    }

    @Override
    public String toString() {
        return "CensusBlock{" +
                "id=" + id +
                ", precinct=" + precinct +
                ", geometry=" + geometry +
                ", isBorderBlock=" + isBorderBlock +
                '}';
    }

}
