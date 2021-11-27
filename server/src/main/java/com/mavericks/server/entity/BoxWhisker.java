package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Basis;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BoxWhiskers")
public class BoxWhisker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "stateId", length = 50, nullable = false)
    private String stateId;

    @Enumerated(EnumType.STRING)
    @Column(name="basisType", nullable=false)
    private Basis basisType;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "boxWhiskerId")
    private List<Box> boxes;

    public BoxWhisker() {}

    public BoxWhisker(String id, String stateId, Basis basisType) {
        this.id = id;
        this.stateId = stateId;
        this.basisType = basisType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Basis getBasisType() {
        return basisType;
    }

    public void setBasisType(Basis basisType) {
        this.basisType = basisType;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
