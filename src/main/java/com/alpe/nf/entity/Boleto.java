package com.alpe.nf.entity;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.FileNotFoundException;

@Data
@AllArgsConstructor
@Builder
public class Boleto {
    private String beneficiario;
    private String pagador;
    private double valor;
    private String dataVencimento;
    private String codigoBoleto;
    private String cpf_Cnpj;
    private String endereco;

    public void salvarEmPdf() throws FileNotFoundException {
        PdfWriter writer = new PdfWriter("Boleto.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        try {
            document.add(new Paragraph("Beneficiario: " + beneficiario));
            document.add(new Paragraph("Pagador: " + pagador));
            document.add(new Paragraph("Valor: " + valor));
            document.add(new Paragraph("Data de Vencimento: " + dataVencimento));
            document.add(new Paragraph("Codigo Boleto: " + codigoBoleto));
            document.add(new Paragraph("Cpf/Cnpj: " + cpf_Cnpj));
            document.add(new Paragraph("Endereco: " + endereco));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Boleto boleto = Boleto.builder()
                .beneficiario("Banco teste")
                .pagador("cliente x")
                .valor(1298.64)
                .dataVencimento("30/04/2024")
                .codigoBoleto("2")
                .cpf_Cnpj("000.000.000-00")
                .endereco("Rua x")
                .build();

        boleto.salvarEmPdf();
    }
}
