package com.mavericks.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Precincts")
public class Precinct {
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "districtId", length = 50, nullable = false)
    private String districtId;

    @Column(name = "number", nullable = false)
    private int number;

    public Precinct() {}

    public Precinct(String id, String districtId, int number) {
        this.id = id;
        this.districtId = districtId;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
