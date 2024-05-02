package com.alpe.nf.repository;

import com.alpe.nf.entity.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    boolean existsByCodigoBoleto(String codigoBoleto);
}
