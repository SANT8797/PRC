package com.example.prc;

public class Contacts
{
    String name,image,status,uid,statusoo;

    public Contacts() {
    }

    public Contacts(String name, String image, String status, String uid, String statusoo) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.uid = uid;
        this.statusoo = statusoo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatusoo() {
        return statusoo;
    }

    public void setStatusoo(String statusoo) {
        this.statusoo = statusoo;
    }
}
