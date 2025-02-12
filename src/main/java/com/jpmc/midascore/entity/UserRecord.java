package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "balance", nullable = false)
    private float balance;

    protected UserRecord() {
    }

    public UserRecord(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', balance='%f']", id, name, balance);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
