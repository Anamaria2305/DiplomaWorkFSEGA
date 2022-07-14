package com.example.blooddonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","requestsList"})
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column(nullable = false,unique = true)
    private String phonenumber;

    @Column(nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_ID")
    private Hospital hospital;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Requests> requestsList;

    public Staff() {
    }

    public Staff(String username, String password, String name, String phonenumber, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
        this.role = role;
    }

    public Staff(String username, String password, String name, String phonenumber, String role, Hospital hospital) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
        this.role = role;
        this.hospital = hospital;
    }

    public Staff(String username, String password, String name, String phonenumber, String role, Hospital hospital, List<Requests> requestsList) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phonenumber = phonenumber;
        this.role = role;
        this.hospital = hospital;
        this.requestsList = requestsList;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getRole() {
        return role;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

}
