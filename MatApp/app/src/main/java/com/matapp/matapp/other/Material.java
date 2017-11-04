package com.matapp.matapp.other;

/**
 * Created by kathrinkoebel on 03.11.17.
 */

public class Material {
    public String title;
    public String description;
    public String owner;
    public String location;
    public String gps;
    public String status;
    public String barcode;
    public String img;
    public String loan_name;
    public String loan_contact;
    public String loan_until;
    public String loan_note;


    public Material(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Material(String title, String description, String owner){
        this.title = title;
        this.description = description;
        this.owner = owner;
    }
}