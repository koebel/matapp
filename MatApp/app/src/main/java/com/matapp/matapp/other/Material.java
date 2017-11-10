package com.matapp.matapp.other;


/**
 *
 * This class is used for creation and management of Material items.
 *
 * Created by kathrinkoebel on 03.11.17.
 *
 */


public class Material {

    /* static Attributes */
    private static int idCounter = 0;
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_LENT = 1;
    public static final int STATUS_UNAVAILABLE = 2;

    /* Material Attributes */
    public int uniqueId;
    public String title;
    public String description;
    public String owner;
    public String location;
    //public String gps;
    public int status;
    public String barcode;
    public String img;
    public String thumb;
    public String loanName;
    public String loanContact;
    public String loanUntil;
    public String loanNote;


    /* Constructor with no arguments */
    public Material(){
        this.uniqueId = createUniqueId();
        this.title = "";
        this.description = "";
        this.owner = "";
        this.location = "";
        //this.gps = "";
        this.status = STATUS_AVAILABLE;
        this.barcode = "";
        this.img = "";
        this.thumb = "";
        this.loanName = "";
        this.loanContact = "";
        this.loanUntil = "";
        this.loanNote = "";
    }

    /* basic Constructor */
    public Material(String title, String description){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.description = description;
        this.owner = "";
        this.location = "";
        //this.gps = "";
        this.status = STATUS_AVAILABLE;
        this.barcode = "";
        this.img = "";
        this.thumb = "";
        this.loanName = "";
        this.loanContact = "";
        this.loanUntil = "";
        this.loanNote = "";
    }

    /* Constructor for initial creation of Materials in MatAddActivity */
    public Material(String title, String description, String owner, String location, int status){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
        //this.gps = "";
        this.barcode = "";
        this.img = "";
        this.thumb = "";
        this.loanName = "";
        this.loanContact = "";
        this.loanUntil = "";
        this.loanNote = "";
    }

    /* full Constructor for creation of Material with all attributes */
    public Material(String title, String description, String owner, String location, int status,
                    String gps, String barcode, String img, String loan_name, String loan_contact, String loan_until, String loan_note){
        this.uniqueId = createUniqueId();
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
        //this.gps = gps;
        this.barcode = barcode;
        this.img = img;
        this.thumb = makeThumb(img);
        this.loanName = loan_name;
        this.loanContact = loan_contact;
        this.loanUntil = loan_until;
        this.loanNote = loan_note;
    }


    /* Getter & Setter Methods */
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

    /*public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }*/

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loan_name) { this.loanName = loan_name; }

    public String getLoanContact() {
        return loanContact;
    }

    public void setLoanContact(String loan_contact) {
        this.loanContact = loan_contact;
    }

    public String getLoanUntil() {
        return loanUntil;
    }

    public void setLoanUntil(String loan_until) {
        this.loanUntil = loan_until;
    }

    public String getLoanNote() {
        return loanNote;
    }

    public void setLoanNote(String loan_note) {
        this.loanNote = loan_note;
    }


    /* static Methods */
    public static synchronized int createUniqueId()
    {
        return idCounter++;
    }


    public String makeThumb(String img) {

        // TODO resize image to thumbnail size (64 x 64dp)
        // https://stackoverflow.com/questions/35195713/re-sizing-an-image-which-in-base64-format-and-converting-to-base64-again-in-java
        // https://www.playframework.com/documentation/1.0/api/play/libs/Images.html
        String thumb = new String("this is a placeholder for the thumbnail size image");

        return thumb;
    }
}