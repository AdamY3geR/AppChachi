/**
 * Represents a member of the application.
 */
package com.example.appchachi;


public class Member {

    // Member attributes
    private String id;
    private String name;
    private String phone;
    private String email;
    private String memberType;


    /**
     * Constructor for creating a Member object.
     *
     * @param id The ID of the member.
     * @param name The name of the member.
     * @param phone The phone number of the member.
     * @param email The email of the member.
     */
    public Member(String id, String name, String phone, String email, String memberType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.memberType = memberType;
    }

    public Member() {

    }

    // Getter and setter methods for member attributes

    /**
     * Gets the MemberType of the member
     *
     * @return the MemberType of the member
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * Gets the ID of the member.
     *
     * @return The ID of the member.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the member.
     *
     * @param id The ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the member.
     *
     * @return The name of the member.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the member.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the member.
     *
     * @return The phone number of the member.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the member.
     *
     * @param phone The phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email of the member.
     *
     * @return The email of the member.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the member.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Checks the status of the member.
     *
     * @return The status of the member.
     */

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }




    /**
     * Returns a string representation of the Member object.
     *
     * @return A string representation of the Member object.
     */
    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
