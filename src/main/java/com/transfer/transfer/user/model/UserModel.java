package com.transfer.transfer.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column
    private String currently;

    @Column
    private double balance;
}
