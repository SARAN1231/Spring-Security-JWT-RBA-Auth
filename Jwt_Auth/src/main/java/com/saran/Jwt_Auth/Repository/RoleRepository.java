package com.saran.Jwt_Auth.Repository;

import com.saran.Jwt_Auth.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String authority);
}
