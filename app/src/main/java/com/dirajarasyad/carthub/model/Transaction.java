package com.dirajarasyad.carthub.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String id;
    private Status status;
    private User user;
    private Item item;
    private Integer quantity;

    public enum Status {
        PENDING("Pending"),
        ON_PROGRESS("On Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String value() {
            return status;
        }

        public static Status fromString(String status) {
            for (Status s : Status.values()) {
                if (s.value().equalsIgnoreCase(status)) {
                    return s;
                }
            }
            throw new Error("No status found with text: " + status);
        }
    }

    public Transaction(String id, Status status, User user, Item item, Integer quantity) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }

    public Transaction(String id, User user, Item item, Integer quantity) {
        this.id = id;
        this.status = Status.PENDING;
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

    public Status getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
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
