package com.example.appchachi;

/**
 * Represents a security personnel, extending the Member class.
 */
public class Security extends Member {

    /**
     * Default constructor required for calls to DataSnapshot.getValue(Security.class)
     */
    public Security() {
        super();
        // Default constructor
    }

    /**
     * Constructs a Security object with the provided parameters.
     *
     * @param id The ID of the security personnel.
     * @param name The name of the security personnel.
     * @param phone The phone number of the security personnel.
     * @param email The email of the security personnel.
     * @param memberType The member type of the security personnel.
     */
    public Security(String id, String name, String phone, String email, String memberType) {
        super(id, name, phone, email, memberType);
    }
}
