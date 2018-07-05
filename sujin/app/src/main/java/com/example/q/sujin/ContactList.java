package com.example.q.sujin;

import android.graphics.Bitmap;

public class ContactList {
    Bitmap contactImage;
    String contactName, contactNumber, contactEmail,contactId;

    public ContactList() {

    }

    public ContactList(String contactId, Bitmap contactImage, String contactName, String contactNumber, String contactEmail) {
        this.contactId = contactId;
        this.contactImage = contactImage;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Bitmap getContactImage() {
        return contactImage;
    }

    public void setContactImage(Bitmap contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() { return contactEmail; }

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }


}