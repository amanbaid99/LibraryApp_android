package com.example.myapplication.signin.authentication;

public class UserDB {
    private String FullName;
    private String number;
    private String email;
    private String Profilrimg;
    private String Pimgname;
    private String uid;


    public UserDB() {
    }

    public UserDB(String fullName, String number, String email, String profilrimg, String pimgname,String uid) {
        if (pimgname.trim().equals("")) {
            pimgname = "No Name";
        }
        FullName = fullName;
        this.number = number;
        this.email = email;
        Profilrimg = profilrimg;
        Pimgname = pimgname;
        this.uid=uid;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilrimg() {
        return Profilrimg;
    }

    public void setProfilrimg(String profilrimg) {
        Profilrimg = profilrimg;
    }

    public String getPimgname() {
        return Pimgname;
    }

    public void setPimgname(String pimgname) {
        Pimgname = pimgname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}