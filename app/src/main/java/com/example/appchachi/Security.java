/**
 * Represents a security personnel, extending the Member class.
 */
package com.example.appchachi;

public class Security extends Member {

    /**
     * Constructs a Security object with the provided parameters.
     *
     * @param id The ID of the security personnel.
     * @param name The name of the security personnel.
     * @param phone The phone number of the security personnel.
     * @param email The email of the security personnel.
     * @param status The status of the security personnel.
     * @param isAdmin Specifies if the security personnel is an admin.
     */
    public Security(String id, String name, String phone, String email, boolean status, boolean isAdmin,String memberType) {
        super(id, name, phone, email, status, isAdmin, memberType);
    }

    // Additional attribute for Security class
    private String position;

    /**
     * Constructs a Security object with the provided parameters, including position.
     *
     * @param id The ID of the security personnel.
     * @param name The name of the security personnel.
     * @param phone The phone number of the security personnel.
     * @param email The email of the security personnel.
     * @param status The status of the security personnel.
     * @param position The position of the security personnel.
     * @param isAdmin Specifies if the security personnel is an admin.
     */
    public Security(String id, String name, String phone, String email, boolean status, String position, boolean isAdmin, String memberType) {
        super(id, name, phone, email, status, isAdmin, memberType);
        this.position = position;
    }

    /**
     * Gets the position of the security personnel.
     *
     * @return The position of the security personnel.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the security personnel.
     *
     * @param position The position to set.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Provides a string representation of the Security object.
     *
     * @return A string representation of the Security object.
     */
    @Override
    public String toString() {
        return "Security{" +
                "position='" + position + '\'' +
                '}';
    }
}
