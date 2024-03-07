package com.example.appchachi;

public class Medic extends Member{
    public Medic(String id, String name, String phone, String email, boolean status, boolean isAdmin) {
        super(id, name, phone, email, status, isAdmin);
    }
    private String equipment;

    public Medic(String id, String name, String phone, String email, boolean status, String equipment, boolean isAdmin) {
        super(id, name, phone, email, status, isAdmin);
        this.equipment = equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
