package com.villive.Backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Building {

    @Id
    private Integer buildingCode;

    private String address;
    private String buildingName;


}
