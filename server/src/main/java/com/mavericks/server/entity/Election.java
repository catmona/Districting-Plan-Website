package com.mavericks.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "regionId", length = 50, nullable = false)
    private String regionId;

    @Column(name = "republicanVotes", nullable = false)
    private double republicanVotes;

    @Column(name = "democraticVotes", nullable = false)
    private double democraticVotes;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "infoId", referencedColumnName = "id")
//    private ElectionInfo info;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "regionId")
//    private  info;

    public Election() {}

    public Election(long id, String regionId, double republicanVotes, double democraticVotes) {
        this.id = id;
        this.regionId = regionId;
        this.republicanVotes = republicanVotes;
        this.democraticVotes = democraticVotes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public double getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(double republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public double getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocraticVotes(double democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

//    public ElectionInfo getInfo() {
//        return info;
//    }
//
//    public void setInfo(ElectionInfo info) {
//        this.info = info;
//    }
}
