package com.matapp.matapp.other;


/**
 * Created by kathrinkoebel on 03.11.17.
 */



public class Material {
    private static int idCounter = 0;
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_LENT = 1;
    public static final int STATUS_UNAVAILABLE = 2;

    public int uniqueId;
    public String title;
    public String description;
    public String owner;
    public String location;
    public String gps;
    public int status;
    public String barcode;
    public String img;
    public String loan_name;
    public String loan_contact;
    public String loan_until;
    public String loan_note;



    /* Constructor */
    public Material(String title, String description){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.owner = "";
        this.location = "";
        this.gps = "";
        this.status = 2;
        this.barcode = "";
        this.img = "";
        this.loan_name = "";
        this.loan_contact = "";
        this.loan_until = "";
        this.loan_note = "";
    }

    public Material(String title, String description, String owner, String location, int status){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
        this.gps = "";
        this.barcode = "";
        this.img = "";
        this.loan_name = "";
        this.loan_contact = "";
        this.loan_until = "";
        this.loan_note = "";
    }

    /* full Constructor */
    public Material(String title, String description, String owner, String location, int status,
                    String gps, String barcode, String img, String loan_name, String loan_contact, String loan_until, String loan_note){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
        this.gps = gps;
        this.barcode = barcode;
        this.img = img;
        this.loan_name = loan_name;
        this.loan_contact = loan_contact;
        this.loan_until = loan_until;
        this.loan_note = loan_note;
    }

    /* Getter & Setter */
    public int getUniqueId() {
        return uniqueId;
    }

    // no Setter for uniqueID, because the Id should never be changed.
    // in case modifictation of the Id is requested, it needs to be checked if new Id is really unique.


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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


    /* static Methods */
    public static synchronized int createUniqueId()
    {
        return idCounter++;
    }
}