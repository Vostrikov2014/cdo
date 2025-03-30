package org.example.authserver.repository;

import org.example.authserver.model.AppUser1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser1, Long> {
    Optional<AppUser1> findByUsername(String username);
}
