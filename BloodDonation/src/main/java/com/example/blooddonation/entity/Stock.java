package com.example.blooddonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","hospital"})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blood_ID")
    private BloodType bloodtype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_ID")
    private Hospital hospital;

    public Stock() {
    }

    public Stock(Double quantity, BloodType bloodtype, Hospital hospital) {
        this.quantity = quantity;
        this.bloodtype = bloodtype;
        this.hospital = hospital;
    }

    public int getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public BloodType getBloodtype() {
        return bloodtype;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }


}
