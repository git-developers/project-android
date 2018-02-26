package com.example.software.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Task implements Serializable  {

    @SerializedName("id_increment")
    private int id;

    @SerializedName("tarea")
    private String tarea;

    @SerializedName("fecha_entrega")
    private String fechaEntrega;

    @SerializedName("nota")
    private String nota;

    @SerializedName("estado")
    private String estado;

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

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getFechaEntregaDate() {
        String[] separated = fechaEntrega.split("T");
        String out = separated[0];
        return out;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNota() {
        return nota;
    }

    public int getNotaInt() {
        return Integer.valueOf(nota);
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public int getEstadoInt() {

        int index = 0;

        switch (estado){
            case "pendiente":
                index = 0;
                break;
            case "no_pendiente":
                index = 1;
                break;
            default:
                index = 0;
                break;
        }

        return index;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return "ic_playlist_play2"; // image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString()  {
        return this.nota + " (Item: "+ this.id+")";
    }
}