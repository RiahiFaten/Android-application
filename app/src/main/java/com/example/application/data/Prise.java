package com.example.application.data;

public class Prise {
    public String numPrise;
    public String description;
    public String date;
    public int qté;
    public String heure;
    public String programmeId;
    public String refMed;
    public String idPatient;

    public Prise() {
    }

    public Prise(String numPrise, String description, String date, int qté, String heure, String programmeId, String refMed, String idPatient) {
        this.numPrise = numPrise;
        this.description = description;
        this.date = date;
        this.qté = qté;
        this.heure = heure;
        this.programmeId = programmeId;
        this.refMed = refMed;
        this.idPatient = idPatient;
    }

    public String getNumPrise() {
        return numPrise;
    }

    public void setNumPrise(String numPrise) {
        this.numPrise = numPrise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQté() {
        return qté;
    }

    public void setQté(int qté) {
        this.qté = qté;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getRefMed() {
        return refMed;
    }

    public void setRefMed(String refMed) {
        this.refMed = refMed;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }
}
