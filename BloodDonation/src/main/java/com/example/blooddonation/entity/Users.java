package com.example.blooddonation.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "appointmentList"})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String cnp;

    @Column(nullable = false,unique = true)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bloodID")
    private BloodType bloodtype;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Appointment> appointmentList;

    public Users() {
    }

    public Users(String name, String password, String username, String cnp, String phone, BloodType bloodtype) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.cnp = cnp;
        this.phone = phone;
        this.bloodtype = bloodtype;
    }

    public Users(String name, String password, String username, String cnp, String phone) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.cnp = cnp;
        this.phone = phone;
    }

    public Users(String name, String password, String username, String cnp, String phone, BloodType bloodtype, List<Appointment> appointmentList) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.cnp = cnp;
        this.phone = phone;
        this.bloodtype = bloodtype;
        this.appointmentList = appointmentList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getCnp() {
        return cnp;
    }

    public String getPhone() {
        return phone;
    }

    public BloodType getBloodtype() {
        return bloodtype;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBloodtype(BloodType bloodtype) {
        this.bloodtype = bloodtype;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }


}
