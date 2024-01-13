package com.example.appchachi;

public class Security extends Member{

    public Security(int id, String name, int phone, String email, String role, boolean status) {
        super(id, name, phone, email, role, status);
    }
    private String position;

    public Security(int id, String name, int phone, String email, String role, boolean status, String position) {
        super(id, name, phone, email, role, status);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
