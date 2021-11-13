package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String test;

    public Districting() {}
    public Districting(int id, String testValue) {
        this.id = id;
        this.test = testValue;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Districting{" +
                "id=" + id +
                ", test='" + test + '\'' +
                '}';
    }
}
