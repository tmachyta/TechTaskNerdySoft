package com.example.techtasknerdysoft.repository;

import com.example.techtasknerdysoft.model.Role;
import com.example.techtasknerdysoft.model.Role.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName roleName);
}
