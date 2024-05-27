package com.example.appchachi;

/**
 * Represents a medical personnel extending the Member class.
 */
public class Medic extends Member {

    /**
     * Default constructor required for calls to DataSnapshot.getValue(Medic.class)
     */
    public Medic() {
        // Default constructor
    }

    /**
     * Constructs a Medic object with specified parameters.
     *
     * @param id The ID of the medic.
     * @param name The name of the medic.
     * @param phone The phone number of the medic.
     * @param email The email of the medic.
     * @param memberType The member type of the medic.
     */
    public Medic(String id, String name, String phone, String email, String memberType) {
        super(id, name, phone, email, memberType);
    }
}
