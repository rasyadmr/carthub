package com.dirajarasyad.carthub.model;

import java.io.Serializable;

public class Cart implements Serializable {
    private String id;
    private User user;
    private Item item;
    private Integer quantity;

    public Cart(String id, User user, Item item, Integer quantity) {
        this.id = id;
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
