package com.multitenant.multitenant.architecture.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
public class TenantData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tenantId;

    private String tenantName;
    private String dbUserName;
    private String dbPassword;
    private String dbName;

    @OneToOne(mappedBy = "tenantData", cascade = CascadeType.ALL)
    private User user;
}
