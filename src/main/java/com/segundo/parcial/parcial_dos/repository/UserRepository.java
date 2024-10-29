package com.segundo.parcial.parcial_dos.repository;

import com.segundo.parcial.parcial_dos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Obtener usuarios "TEACHER"
    List<User> findByRoles_RolName(String name);

    Optional<User> findByUsername(String username);


}
