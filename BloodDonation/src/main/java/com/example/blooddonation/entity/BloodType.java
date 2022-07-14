package com.example.blooddonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bloodtype")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users","stocks","requestsList"})
public class BloodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String blood;

    @Column(nullable = false)
    private String rh;

    @OneToMany(mappedBy = "bloodtype", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Users> users;

    @OneToMany(mappedBy = "bloodtype", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Stock> stocks;

    @OneToMany(mappedBy = "bloodtype", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Requests> requestsList;

    public int getId() {
        return id;
    }

    public String getBlood() {
        return blood;
    }

    public String getRh() {
        return rh;
    }

    public List<Users> getUsers() {
        return users;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodType bloodType = (BloodType) o;
        return Objects.equals(blood, bloodType.blood) && Objects.equals(rh, bloodType.rh);
    }


}
