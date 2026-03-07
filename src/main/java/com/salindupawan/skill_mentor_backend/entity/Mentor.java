package com.salindupawan.skill_mentor_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mentor")
public class Mentor {
    @Id
    @GeneratedValue
    private Long id;

}
