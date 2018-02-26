package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jafeth on 3/30/17.
 */

public class Courses implements Serializable {

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
        return "ic_content_paste"; //image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}