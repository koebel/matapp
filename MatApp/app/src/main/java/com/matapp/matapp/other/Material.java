package com.matapp.matapp.other;


/**
 * Created by kathrinkoebel on 03.11.17.
 */

public class Material {
    public int uniqueId;
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



    /* Constructor */
    public Material(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Material(String title, String description, String owner, String location){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
    }

    /* Getter & Setter */
    public int getUniqueId() {
        return uniqueId;
    }

    // kein Setter, da ID nicht verändert werden darf
    // sonst müsste geprüft werden, dass die neue ID wirklich unique ist :)
    /*
    public int setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLoanName() {
        return loan_name;
    }

    public void setLoanName(String loan_name) {
        this.loan_name = loan_name;
    }

    public String getLoanContact() {
        return loan_contact;
    }

    public void setLoanContact(String loan_contact) {
        this.loan_contact = loan_contact;
    }

    public String getLoanUntil() {
        return loan_until;
    }

    public void setLoanUntil(String loan_until) {
        this.loan_until = loan_until;
    }

    public String getLoanNote() {
        return loan_note;
    }

    public void setLoanNote(String loan_note) {
        this.loan_note = loan_note;
    }

}