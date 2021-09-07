package com.brunomilitzer.springsecurity.repositories;

import com.brunomilitzer.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
