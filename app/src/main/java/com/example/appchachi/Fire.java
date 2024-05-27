package com.example.appchachi;

/**
 * Class representing fire department personnel.
 */
public class Fire extends Member{
    /**
     * Constructor for creating a Fire object.
     *
     * @param id The ID of the fire department personnel.
     * @param name The name of the fire department personnel.
     * @param phone The phone number of the fire department personnel.
     * @param email The email address of the fire department personnel.
     */
    public Fire(String id, String name, String phone, String email,String memberType) {
        super(id, name, phone, email,memberType);
    }
}
