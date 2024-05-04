package com.alpe.nf.adapter.utils;

import com.alpe.nf.adapter.util.ExtracaoDadosUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtracaoDadosUtilsTest {

    @Test
    public void testExtrairCodigoBoleto() {
        String textoPDF = "Codigo Boleto: 123456\r\n";
        assertEquals("123456", ExtracaoDadosUtils.extrairCodigoBoleto(textoPDF));
    }

    @Test
    public void testExtrairCpfCnpj() {
        String textoPDF = "Cpf/Cnpj: 123.456.789-00\r\n";
        assertEquals("123.456.789-00", ExtracaoDadosUtils.extrairCpfCnpj(textoPDF));
    }

    @Test
    public void testExtrairDataVencimento() {
        String textoPDF = "Data de Vencimento: 01/01/2024\r\n";
        assertEquals(LocalDate.of(2024, 1, 1), ExtracaoDadosUtils.extrairDataVencimento(textoPDF));
    }

    @Test
    public void testExtrairValor() {
        String textoPDF = "Valor: 100.50\r\n";
        assertEquals(new BigDecimal("100.50"), ExtracaoDadosUtils.extrairValor(textoPDF));
    }

    @Test
    public void testExtrairBeneficiario() {
        String textoPDF = "Beneficiario: Empresa XYZ\r\n";
        assertEquals("Empresa XYZ", ExtracaoDadosUtils.extrairBeneficiario(textoPDF));
    }

    @Test
    public void testExtrairPagador() {
        String textoPDF = "Pagador: João Silva\r\n";
        assertEquals("João Silva", ExtracaoDadosUtils.extrairPagador(textoPDF));
    }

    @Test
    public void testExtrairEndereco() {
        String textoPDF = "Endereco: Rua ABC, 123\r\n";
        assertEquals("Rua ABC, 123", ExtracaoDadosUtils.extrairEndereco(textoPDF));
    }
}