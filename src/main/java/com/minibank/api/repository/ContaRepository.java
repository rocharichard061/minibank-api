package com.minibank.api.repository;

import com.minibank.api.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository <Conta, UUID> {



    Optional<Conta> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
