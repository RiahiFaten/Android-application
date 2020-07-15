package com.example.application.data;


public class Programme {
    public String idP;
    public String nom;
    public String date_debut;
    public int durée;
    public String maladie;
    public String idPatient;

    public Programme() {
    }

    public Programme(String idP, String nom, String date_debut, int durée, String maladie, String idPatient) {
        this.idP = idP;
        this.nom = nom;
        this.date_debut = date_debut;
        this.durée = durée;
        this.maladie = maladie;
        this.idPatient = idPatient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdP() {
        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public int getDurée() {
        return durée;
    }

    public void setDurée(int durée) {
        this.durée = durée;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }


    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }


}