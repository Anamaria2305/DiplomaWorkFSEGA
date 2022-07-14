package com.example.blooddonation.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "requests")
public class Requests {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String targetName;

    @Column(nullable = false)
    private String illness;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(nullable = false)
    private Integer confirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blood_ID")
    private BloodType bloodtype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_ID")
    private Staff staff;

    public Requests() {
    }

    public Requests(String targetName, String illness, Date requestDate, Integer confirmed, BloodType bloodtype, Staff staff) {
        this.targetName = targetName;
        this.illness = illness;
        this.requestDate = requestDate;
        this.confirmed = confirmed;
        this.bloodtype = bloodtype;
        this.staff = staff;
    }

    public int getId() {
        return id;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getIllness() {
        return illness;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public BloodType getBloodtype() {
        return bloodtype;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
