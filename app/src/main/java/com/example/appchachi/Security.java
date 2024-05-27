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
        super(id, name, phone, email, memberType);
    }


    public Security(String userID, String name, String phone, String email, String memberType) {
        super(userID, name, phone, email, memberType);
    }
}
