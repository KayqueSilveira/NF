package com.alpe.nf.adapter.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExtracaoDadosUtils {

    public static String extrairCodigoBoleto(String textoPDF) {
        Pattern pattern = Pattern.compile("Codigo Boleto: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? matcher.group(1) : null;
    }
    public static String extrairCpfCnpj(String textoPDF) {
        Pattern pattern = Pattern.compile("Cpf/Cnpj: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static LocalDate extrairDataVencimento(String textoPDF) {
        Pattern pattern = Pattern.compile("Data de Vencimento: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    public static BigDecimal extrairValor(String textoPDF) {
        Pattern pattern = Pattern.compile("Valor: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? new BigDecimal(matcher.group(1)) : null;
    }

    public static String extrairBeneficiario(String textoPDF) {
        Pattern pattern = Pattern.compile("Beneficiario: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static String extrairPagador(String textoPDF) {
        Pattern pattern = Pattern.compile("Pagador: (.*?)\r\n");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static String extrairEndereco(String textoPDF) {
        Pattern pattern = Pattern.compile("Endereco: (.*?)$");
        Matcher matcher = pattern.matcher(textoPDF);
        return matcher.find() ? matcher.group(1) : null;
    }
}
