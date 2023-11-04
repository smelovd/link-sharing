package com.shimada.linksv4.web.repository;

import com.shimada.linksv4.models.ERole;
import com.shimada.linksv4.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
