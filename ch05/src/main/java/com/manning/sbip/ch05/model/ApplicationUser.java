package com.manning.sbip.ch05.model;

import lombok.Data;

import jakarta.persistence.*;

//스프링 시큐리티는 기본 JDBC 인증을 이용해 사용자 인증을 시행하기 위해선 USER라는 테이블명을 가져야한다.
//하지만 다른 테이블 명을 사용해 UserDetailService에 등록해주면 커스텀한 테이블로 사용이 가능하다.
@Entity
@Table(name = "CT_USERS")
@Data
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private boolean verified;
    private boolean locked;
    @Column(name = "ACC_CRED_EXPIRED")
    private boolean accountCredentialsExpired;
}
