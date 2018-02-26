package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

public class Attendance {

    public static final int NONE = 1000; // No answer selected
    public static final int FALTA = 0;
    public static final int ASISTIO = 1;
    public static final int TARDANZA = 2;

    public int current = NONE; // hold the answer picked by the user, initial is NONE(see below)

    @SerializedName("id_increment")
    private int id;

    @SerializedName("option_status")
    private int optionStatus;

    @SerializedName("option_date")
    private String optionDate;


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(int optionStatus) {
        this.optionStatus = optionStatus;
    }

    public String getOptionDate() {
        return optionDate;
    }

    public void setOptionDate(String optionDate) {
        this.optionDate = optionDate;
    }
}