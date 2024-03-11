/**
 * Represents a member of the application.
 */
package com.example.appchachi;

//todo - enabling broadcast+notification
//todo - firebase ml messaging
//todo - for admin user-> adding a create and send survey to listed members of the kita
//todo - after adding authentication that it will add automatically to the lv
//todo - announcement will show who sent it


//todo - add remember me after login
//todo - i need that after the sign up or log in it will send him to his page according to his member type, there's three fragments security medic and fire
//todo - add no same email
//todo - fix login successful everytime
//todo - add forgot password

public class Member {

    // Member attributes
    private String id;
    private String name;
    private String phone;
    private String email;
    private boolean status;
    private Boolean isAdmin;

    /**
     * Constructor for creating a Member object.
     *
     * @param id The ID of the member.
     * @param name The name of the member.
     * @param phone The phone number of the member.
     * @param email The email of the member.
     * @param status The status of the member.
     * @param isAdmin Specifies if the member is an admin.
     */
    public Member(String id, String name, String phone, String email, boolean status, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.isAdmin = isAdmin;
    }

    // Getter and setter methods for member attributes

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
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status of the member.
     *
     * @param status The status to set.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Checks if the member is an admin.
     *
     * @return True if the member is an admin, otherwise false.
     */
    public Boolean getAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin status of the member.
     *
     * @param admin The admin status to set.
     */
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
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
                ", status=" + status +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
