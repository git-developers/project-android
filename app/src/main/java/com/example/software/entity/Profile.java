package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jafeth on 3/30/17.
 */

public class Profile implements Serializable {

    @SerializedName("permission")
    private Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}