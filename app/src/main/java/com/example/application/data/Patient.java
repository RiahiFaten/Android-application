package com.example.application.data;
import com.google.firebase.database.IgnoreExtraProperties;

public class Patient {
    private String patientId;
    private String patientMail;

    public Patient() {
    }

    public Patient(String patientId, String patientMail) {
        this.patientId = patientId;
        this.patientMail = patientMail;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientMail() {
        return patientMail;
    }

    public void setPatientMail(String patientMail) {
        this.patientMail = patientMail;
    }
}

