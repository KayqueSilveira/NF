package com.alpe.nf;

import com.alpe.nf.entity.Boleto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class NfApplication {

	public static void main(String[] args) throws FileNotFoundException {
		Boleto boleto = Boleto.builder().codigoBoleto("1").valor(100.00).beneficiario("banco x").cpf_Cnpj("000.000.000.-00").dataVencimento("04/05/2024").pagador("cliente x").build();
		boleto.salvarEmPdf();
		//SpringApplication.run(NfApplication.class, args);
	}

}
