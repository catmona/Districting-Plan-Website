package com.mavericks.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="date", nullable=false)
    private Date date;

    public Election() {}

    public Election(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
