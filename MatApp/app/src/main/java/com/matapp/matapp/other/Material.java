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
    public static final int STATUS_AVAILABLE = 0;
    public static final int STATUS_LENT = 1;
    public static final int STATUS_UNAVAILABLE = 2;

    /* Material Attributes */
    private String title = "";
    private String description = "";
    private String owner = "";
    private String location = "";
    private int status;
    private String barcode = "";
    private String img = "";
    private String thumb = "";
    private String loanName = "";
    private String loanContact = "";
    private String loanUntil = "";
    private String loanNote = "";


    /* Constructor with no arguments */
    public Material () {
    }

    /* basic Constructor */
    public Material(String title, String description){
        this.title = title;
        this.description = description;
        this.status = STATUS_AVAILABLE;
    }

    /* Constructor for initial creation of Materials in MatAddActivity */
    public Material(String title, String description, String owner, String location, int status){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
    }

    /* full Constructor for creation of Material with all attributes */
    public Material(String title, String description, String owner, String location, int status,
                    String barcode, String img, String loanName, String loanContact, String loanUntil, String loanNote){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.status = status;
        this.barcode = barcode;
        this.img = img;
        this.loanName = loanName;
        this.loanContact = loanContact;
        this.loanUntil = loanUntil;
        this.loanNote = loanNote;
    }


    /* Getter & Setter Methods */

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

    public void setLoanName(String loanName) { this.loanName = loanName; }

    public String getLoanContact() {
        return loanContact;
    }

    public void setLoanContact(String loanContact) {
        this.loanContact = loanContact;
    }

    public String getLoanUntil() {
        return loanUntil;
    }

    public void setLoanUntil(String loanUntil) {
        this.loanUntil = loanUntil;
    }

    public String getLoanNote() {
        return loanNote;
    }

    public void setLoanNote(String loanNote) {
        this.loanNote = loanNote;
    }
}