package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jafeth on 3/30/17.
 */

public class Grades implements Serializable {

    @SerializedName("id_increment")
    private int id;

    @SerializedName("bimester")
    private int bimester;

    @SerializedName("note_monthly")
    private int gradeMonthly;

    @SerializedName("note_bimonthly")
    private int gradeBimonthly;

    @SerializedName("note_teacher")
    private int gradeTeacher;

    // Image name (Without extension)
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBimester() {
        return bimester;
    }

    public void setBimester(int bimester) {
        this.bimester = bimester;
    }

    public int getGradeMonthly() {
        return gradeMonthly;
    }

    public void setGradeMonthly(int gradeMonthly) {
        this.gradeMonthly = gradeMonthly;
    }

    public int getGradeBimonthly() {
        return gradeBimonthly;
    }

    public void setGradeBimonthly(int gradeBimonthly) {
        this.gradeBimonthly = gradeBimonthly;
    }

    public int getGradeTeacher() {
        return gradeTeacher;
    }

    public void setGradeTeacher(int gradeTeacher) {
        this.gradeTeacher = gradeTeacher;
    }

    public String getImage() {
        return "ic_ac_unit"; //image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}