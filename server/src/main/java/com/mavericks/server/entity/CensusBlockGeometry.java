package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "CensusBlockGeometries")
public class CensusBlockGeometry {
    @Id
    @Column(name="id", nullable=false)
    private long id;

    @Convert(converter = GeometryConverterString.class)
    @Column(name="geometry")
    private Geometry geometry;

    public CensusBlockGeometry() {}

    public CensusBlockGeometry(long id, Geometry geometry) {
        this.id = id;
        this.geometry = geometry;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
