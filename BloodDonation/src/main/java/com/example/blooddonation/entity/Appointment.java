package com.example.blooddonation.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date app_date;

    @Column(nullable = false)
    private Integer confirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_ID")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_ID")
    private Users users;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Bloodtests bloodtests;

    public Appointment(Date app_date, Integer confirmed, Hospital hospital, Users users) {
        this.app_date = app_date;
        this.confirmed = confirmed;
        this.hospital = hospital;
        this.users = users;
    }

    public Appointment() {

    }

    public Appointment(Date app_date, Integer confirmed, Hospital hospital, Users users, Bloodtests bloodtests) {
        this.app_date = app_date;
        this.confirmed = confirmed;
        this.hospital = hospital;
        this.users = users;
        this.bloodtests = bloodtests;
    }

    public int getId() {
        return id;
    }

    public Date getApp_date() {
        return app_date;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Users getUsers() {
        return users;
    }

    public Bloodtests getBloodtests() {
        return bloodtests;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setBloodtests(Bloodtests bloodtests) {
        this.bloodtests = bloodtests;
    }


}
