package com.example.blooddonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hospitals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "staffList","appointmentList"})
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private String address;

    @Column(nullable = false,unique = true)
    private String phonenumber;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Staff> staffList;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Stock> stockList;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Appointment> appointmentList;

    public Hospital() {
    }

    public Hospital(String name, String address, String phonenumber) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public Hospital(String name, String address, String phonenumber, List<Staff> staffList) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.staffList = staffList;
    }

    public Hospital(String name, String address, String phonenumber, List<Staff> staffList, List<Stock> stockList, List<Appointment> appointmentList) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.staffList = staffList;
        this.stockList = stockList;
        this.appointmentList = appointmentList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

}
