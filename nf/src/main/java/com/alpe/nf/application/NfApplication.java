package com.alpe.nf.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NfApplication {

	public static void main(String[] args) {
		/*Boleto boleto = Boleto.builder()
				.beneficiario("Daycoval")
				.valor(1298.64)
				.pagador("Kayque Garcia")
				.dataVencimento("30/04/2024")
				.codigoBoleto("2")
				.endereco("Rua parana")
				.cpf_Cnpj("446.117.958-37")
				.build();
		boleto.salvarEmPdf();*/
		SpringApplication.run(NfApplication.class, args);
	}

}
