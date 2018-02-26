package com.example.software.entity;

import com.example.software.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jafeth on 3/30/17.
 */

public class Notice implements Serializable {

    public static final String MENSAJE = "Mensaje";
    public static final String CITACION = "Citacion";
    public static final String INCIDENTE = "Incidente";

    public static final int MENSAJE_INT = 1;
    public static final int CITACION_INT = 2;
    public static final int INCIDENTE_INT = 3;

    @SerializedName("id_increment")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("message_type")
    private String messageType;

    @SerializedName("created_at")
    private String createdAt;

    // Image name (Without extension)
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {

        String out = "";
        int type = Integer.parseInt(messageType);

        switch (type){
            case MENSAJE_INT:
                out = MENSAJE;
                break;
            case CITACION_INT:
                out = CITACION;
                break;
            case INCIDENTE_INT:
                out = INCIDENTE;
                break;
        }

        return out;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return "ic_announcement"; // image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}