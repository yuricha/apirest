package com.example.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRespository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT token FROM client WHERE clientId = ?1 and clientSecret = ?2", nativeQuery = true)
    String findByClientAcces(String id,String clientSecret);

    @Query(value = "SELECT clientId FROM client WHERE token = ?1", nativeQuery = true)
    String findByToken(String token);
}