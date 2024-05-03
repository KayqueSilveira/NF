package com.alpe.nf.adapter.repository;


import com.alpe.nf.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNome(String nomeCliente);
    boolean existsByNome (String nomeCliente);
}
