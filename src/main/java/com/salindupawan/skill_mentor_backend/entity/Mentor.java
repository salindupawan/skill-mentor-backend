package com.salindupawan.skill_mentor_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "mentor")
@Data
public class Mentor {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "profession")
    private String profession;

    @Column(name = "company")
    private String company;

    @Column(name = "expericence_years")
    private Integer experienceYears;

    @Column(name = "bio", length = 255)
    private String bio;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "is_certified")
    private Boolean isCertified;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "specialization")
    private String specialization;

    @OneToMany(mappedBy = "mentor")
    private List<Review> reviews;
}
