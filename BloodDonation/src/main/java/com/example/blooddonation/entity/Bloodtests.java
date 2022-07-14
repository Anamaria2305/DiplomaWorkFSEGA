package com.example.blooddonation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "bloodtests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "appointment"})
public class Bloodtests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer sida;

    @Column
    private Integer leucemie;

    @Column
    private Integer hepatitab;

    @Column
    private Integer hepatitac;

    @Column
    private Double trombocite;

    @Column
    private Double hemoglobina;

    @Column
    private Double colesterol;

    @Column
    private Double leucocite;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_ID", referencedColumnName = "id")
    private Appointment appointment;

    public Bloodtests(Integer sida, Integer leucemie, Integer hepatitab, Integer hepatitac, Double trombocite, Double hemoglobina, Double colesterol, Double leucocite, Appointment appointment) {
        this.sida = sida;
        this.leucemie = leucemie;
        this.hepatitab = hepatitab;
        this.hepatitac = hepatitac;
        this.trombocite = trombocite;
        this.hemoglobina = hemoglobina;
        this.colesterol = colesterol;
        this.leucocite = leucocite;
        this.appointment = appointment;
    }

    public Bloodtests() {

    }

    public int getId() {
        return id;
    }

    public Integer getSida() {
        return sida;
    }

    public Integer getLeucemie() {
        return leucemie;
    }

    public Integer getHepatitab() {
        return hepatitab;
    }

    public Integer getHepatitac() {
        return hepatitac;
    }

    public Double getTrombocite() {
        return trombocite;
    }

    public Double getHemoglobina() {
        return hemoglobina;
    }

    public Double getColesterol() {
        return colesterol;
    }

    public Double getLeucocite() {
        return leucocite;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setSida(Integer sida) {
        this.sida = sida;
    }

    public void setLeucemie(Integer leucemie) {
        this.leucemie = leucemie;
    }

    public void setHepatitab(Integer hepatitab) {
        this.hepatitab = hepatitab;
    }

    public void setHepatitac(Integer hepatitac) {
        this.hepatitac = hepatitac;
    }

    public void setTrombocite(Double trombocite) {
        this.trombocite = trombocite;
    }

    public void setHemoglobina(Double hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public void setColesterol(Double colesterol) {
        this.colesterol = colesterol;
    }

    public void setLeucocite(Double leucocite) {
        this.leucocite = leucocite;
    }


}
