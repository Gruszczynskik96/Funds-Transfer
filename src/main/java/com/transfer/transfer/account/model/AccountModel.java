package com.transfer.transfer.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "Balance cannot be null!")
    private double balance;

    @Column(name = "currency")
    @NotBlank(message = "Currency cannot be blank or null!")
    private String currency;

    @Column(name = "userID", unique = true)
    @NotNull(message = "User ID cannot be null!")
    private long userID;
}
