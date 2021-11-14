package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "Precincts")
public class Precinct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private District district;

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
}
