package com.mavericks.server.entity;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "States")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Transient
    private Point center;
   // private int[] center;
    //private BoxWhisker boxWhisker;

    @OneToOne
    @JoinColumn(name = "enacted_id")
    private Districting enacted;

    @OneToMany
    private List<Districting> districtings;

    public State() {}
    public State(int id, String name, Point center) {
        this.id = id;
        this.name = name;
        this.center = center;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public Districting getEnacted() {
        return enacted;
    }

    public void setEnacted(Districting enacted) {
        this.enacted = enacted;
    }

    public List<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", center='" + center + '\'' +
                '}';
    }
}