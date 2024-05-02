package com.multitenant.multitenant.architecture.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private int userId;
    @Column(name="name")
    private String name;
    @Column(name="username")
    private String userName;
    @Column(name="password")
    private String password;

    @OneToOne
    @JoinColumn(name = "tenantId")
    private TenantData tenantData;
}
