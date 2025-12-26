package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String major;

    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int grade;

    private String userStatus;

    private int totalSemesters;

    private int readingVerifiedSemesters;

    private String readingCertification;

    // User 1:1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
        if (user.getProfile() != this) {
            user.setProfile(this);
        }
    }
}
