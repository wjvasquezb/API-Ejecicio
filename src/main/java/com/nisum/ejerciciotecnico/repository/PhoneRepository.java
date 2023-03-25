package com.nisum.ejerciciotecnico.repository;

import com.nisum.ejerciciotecnico.entitie.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID> {
}
