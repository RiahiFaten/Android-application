package com.example.application.data;

public class Temperature {
    public String numTemp;
    public String degre;
    public String dateT;
    public String numP;

    public Temperature() {
    }

    public Temperature(String numTemp, String degre, String dateT, String numP) {
        this.numTemp = numTemp;
        this.degre = degre;
        this.dateT = dateT;
        this.numP = numP;
    }

    public String getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(String numTemp) {
        this.numTemp = numTemp;
    }

    public String getDegre() {
        return degre;
    }

    public void setDegre(String degre) {
        this.degre = degre;
    }

    public String getDateT() {
        return dateT;
    }

    public void setDateT(String dateT) {
        this.dateT = dateT;
    }

    public String getNumP() {
        return numP;
    }

    public void setNumP(String numP) {
        this.numP = numP;
    }
}
