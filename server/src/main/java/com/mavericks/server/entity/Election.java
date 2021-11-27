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
    private int republicanVotes;

    @Column(name = "democraticVotes", nullable = false)
    private int  democraticVotes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "infoId", referencedColumnName = "id")
    private ElectionInfo info;

    public Election() {}

    public Election(long id, String regionId, int republicanVotes, int democraticVotes) {
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

    public int getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(int republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public int getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocraticVotes(int democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    public ElectionInfo getInfo() {
        return info;
    }

    public void setInfo(ElectionInfo info) {
        this.info = info;
    }
}
