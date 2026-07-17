package com.example.mubashir;

public class BasicInfo {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public BasicInfo(String name, String email, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public String getName(){return name;}
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
