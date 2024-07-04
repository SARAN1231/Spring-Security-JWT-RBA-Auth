package com.saran.Jwt_Auth.Repository;

import com.saran.Jwt_Auth.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Boolean existsByUsername(String username);

    Optional<UserModel> findByUsername(String username);
}
