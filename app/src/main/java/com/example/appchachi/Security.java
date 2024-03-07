package com.example.appchachi;

public class Security extends Member{

    public Security(String id, String name, String phone, String email, boolean status, boolean isAdmin) {
        super(id, name, phone, email, status, isAdmin);
    }
    private String position;

    public Security(String id, String name, String phone, String email, boolean status, String position, boolean isAdmin) {
        super(id, name, phone, email, status, isAdmin);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Security{" +
                "position='" + position + '\'' +
                '}';
    }
}
