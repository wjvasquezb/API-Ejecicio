package com.nisum.ejerciciotecnico.repository;

import com.nisum.ejerciciotecnico.entitie.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findUserEntitiesByNameAndPassword(String userName, String password);
    UserEntity findUserEntitiesByName(String name);
    UserEntity findUserEntitiesByToken(String token);
    UserEntity findUserEntitiesByEmail(String email);


}