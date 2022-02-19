package com.transfer.transfer.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserModel {
    @Column
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @Column
    private double balance;

    @Column
    private String currency;

    @Column(unique = true)
    private long userID;
}
