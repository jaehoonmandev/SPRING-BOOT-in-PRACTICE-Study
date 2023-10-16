package com.manning.sbip.ch06.repository;

import com.manning.sbip.ch06.model.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerficationRepository extends CrudRepository<EmailVerification,  String> {
    EmailVerification findByUsername(String userName);
    boolean existsByUsername(String userName);
}
