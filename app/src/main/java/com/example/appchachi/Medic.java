package com.example.appchachi;

public class Medic extends Member{
    public Medic(String id, String name, String phone, String email, String role, boolean status) {
        super(id, name, phone, email, role, status);
    }
    private String equipment;

    public Medic(String id, String name, String phone, String email, String role, boolean status, String equipment) {
        super(id, name, phone, email, role, status);
        this.equipment = equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
