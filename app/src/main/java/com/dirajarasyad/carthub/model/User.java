package com.dirajarasyad.carthub.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class User implements Serializable {
    private String id, username, password, email, phone, address;
    private Drawable image;
    private Role role;

    public enum Role {
        ADMIN("Admin"),
        SELLER("Seller"),
        REQUESTED("Request Seller"),
        NORMAL("Normal");

        private final String role;

        Role(String role) {
            this.role = role;
        }

        public String value() {
            return role;
        }

        public static User.Role fromString(String role) {
            for (User.Role r : User.Role.values()) {
                if (r.value().equalsIgnoreCase(role)) {
                    return r;
                }
            }
            throw new Error("No status found with text: " + role);
        }
    }

    public User(String id, String username, String password, String email, String phone, String address, Drawable image, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
