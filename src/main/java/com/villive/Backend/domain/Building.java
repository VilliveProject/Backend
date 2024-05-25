package com.villive.Backend.domain;

import jakarta.persistence.*;

@Entity
public class Building {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "building_code")
    private String buildingCode;

    private String address;

    @Column(name = "building_name")
    private String buildingName;

}
