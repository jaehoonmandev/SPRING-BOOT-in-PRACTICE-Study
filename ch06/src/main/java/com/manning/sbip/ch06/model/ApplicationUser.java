package com.manning.sbip.ch06.model;

import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "CT_USERS")
@Data
public class ApplicationUser {
    // 사용자 정보를 DB에 저장하기 위한 모델.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private boolean verified = true;
}
