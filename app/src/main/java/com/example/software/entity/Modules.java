package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Modules implements Serializable  {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    // Image name (Without extension)
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString()  {
        return this.name + " (Item: "+ this.id+")";
    }
}