/**
 * Represents a medical personnel extending the Member class.
 */
package com.example.appchachi;

public class Medic extends Member{
    /**
     * Constructs a Medic object with specified parameters.
     *
     * @param id The ID of the medic.
     * @param name The name of the medic.
     * @param phone The phone number of the medic.
     * @param email The email of the medic.
     * @param status The status of the medic.
     * @param isAdmin Indicates whether the medic is an admin or not.
     */
    public Medic(String id, String name, String phone, String email, boolean status, boolean isAdmin, String memberType) {
        super(id, name, phone, email, status, isAdmin,memberType);
    }

    private String equipment; // Equipment used by the medic

    /**
     * Constructs a Medic object with additional equipment parameter.
     *
     * @param id The ID of the medic.
     * @param name The name of the medic.
     * @param phone The phone number of the medic.
     * @param email The email of the medic.
     * @param status The status of the medic.
     * @param equipment The equipment used by the medic.
     * @param isAdmin Indicates whether the medic is an admin or not.
     */
    public Medic(String id, String name, String phone, String email, boolean status, String equipment, boolean isAdmin,String memberType) {
        super(id, name, phone, email, status, isAdmin,memberType);
        this.equipment = equipment;
    }

    /**
     * Gets the equipment used by the medic.
     *
     * @return The equipment used by the medic.
     */
    public String getEquipment() {
        return equipment;
    }

    /**
     * Sets the equipment used by the medic.
     *
     * @param equipment The equipment to be set.
     */
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
