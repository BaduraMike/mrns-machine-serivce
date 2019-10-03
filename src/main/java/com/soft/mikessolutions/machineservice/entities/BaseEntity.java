package com.soft.mikessolutions.machineservice.entities;

import javax.persistence.*;

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identityNumber;

    public Long getIdentityNumber() {
        return identityNumber;
    }
}
