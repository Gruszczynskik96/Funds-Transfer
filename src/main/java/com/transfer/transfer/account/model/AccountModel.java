package com.transfer.transfer.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountModel {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "userID", unique = true)
    private long userID;
}
