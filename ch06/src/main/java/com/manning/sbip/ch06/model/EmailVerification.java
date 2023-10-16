package com.manning.sbip.ch06.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "CT_EMAIL_VERIFICATIONS")
@NoArgsConstructor //인자 없는 생성자

public class EmailVerification {

    //CT_USERS의 username과 CT_EMAIL_VERIFICATION의 verificationId를 맵핑하기 위한 테이블.
    @Id
    @GeneratedValue(generator = "UUID_GENERATOR")
    @GenericGenerator(
            name = "UUID_GENERATOR",
            strategy ="org.hibernate.id.UUIDGenerator"
    )
    private String verificationId; //ID 생성값을 UUID로 지정한다.

    private String username; // 비교를 위한 username

    //생성자
    public EmailVerification(String username) {
        this.username = username;
    }
}
