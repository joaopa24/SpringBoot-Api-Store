package com.xbeast.xbeast.repository;

import com.xbeast.xbeast.data.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    UserDetails findByEmail(String email);
}
