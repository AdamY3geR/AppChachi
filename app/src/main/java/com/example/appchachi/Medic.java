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
     */
    public Medic(String id, String name, String phone, String email, String memberType) {
        super(id, name, phone, email,memberType);
    }


}
