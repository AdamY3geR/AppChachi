package com.example.appchachi;

public class Security extends Member{

    public Security(String id, String name, String phone, String email, String role, boolean status) {
        super(id, name, phone, email, role, status);
    }
    private String position;

    public Security(String id, String name, String phone, String email, String role, boolean status, String position) {
        super(id, name, phone, email, role, status);
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
