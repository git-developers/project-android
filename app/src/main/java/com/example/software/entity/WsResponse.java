package com.example.software.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class WsResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    public WsResponse() {
        this.status = -1;
        this.message = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
