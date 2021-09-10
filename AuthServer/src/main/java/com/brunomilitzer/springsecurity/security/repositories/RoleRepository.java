package com.brunomilitzer.springsecurity.security.repositories;

import com.brunomilitzer.springsecurity.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
