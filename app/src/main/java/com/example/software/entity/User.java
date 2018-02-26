package com.example.software.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by jafeth on 3/30/17.
 */

public class User implements Serializable {

    public static final String INSERT_TYPE_LOGIN = "insert_type_login";
    public static final String INSERT_TYPE_SELECTED_STUDENT = "insert_type_selected_student";
    public static final String INSERT_TYPE_CHILDREN = "insert_type_children";

    @SerializedName("id_increment")
    private int idIncrement;

    @SerializedName("username")
    private String username;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("role")
    private String role;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("active")
    private boolean active;

    @SerializedName("profile")
    private Profile profile;

    // Image name (Without extension)
    @SerializedName("image")
    private String image;

    @SerializedName("insert_type")
    private String insertType;

    private Attendance attendance;

    public User() {

    }

    public User(String username, String slug) {
        this.username = username;
        this.slug = slug;
        this.active= true;
    }

    public int getIdIncrement() {
        return idIncrement;
    }

    public void setIdIncrement(int idIncrement) {
        this.idIncrement = idIncrement;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        try{
            return name.trim();
        }catch (NullPointerException e){
            return "";
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getImage() {
        return "ic_person"; //image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInsertType() {
        return insertType;
    }

    public void setInsertType(String insertType) {
        this.insertType = insertType;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public int getAttendanceOrNull() {

        if(attendance != null){
            return attendance.getCurrent();
        }

        return Attendance.FALTA;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return this.username +" ("+ this.slug +")";
    }

}