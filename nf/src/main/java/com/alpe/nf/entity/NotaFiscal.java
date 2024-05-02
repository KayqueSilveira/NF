package com.alpe.nf.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beneficiario;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String codigoBoleto;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
