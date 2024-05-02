package com.alpe.nf.service.usecase;


import com.alpe.nf.entity.Cliente;
import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.exception.ExtracaoDadosException;
import com.alpe.nf.repository.ClienteRepository;
import com.alpe.nf.repository.NotaFiscalRepository;
import com.alpe.nf.util.ExtracaoDadosUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.alpe.nf.util.ExtracaoDadosUtils.*;

@Component
public class ExtrairDadosNotaFiscalUseCase {

    private final NotaFiscalRepository notaFiscalRepository;
    private final ExtracaoDadosUtils extracaoDadosUtils;
    private final ClienteRepository clienteRepository;

    @Autowired
    public ExtrairDadosNotaFiscalUseCase(NotaFiscalRepository notaFiscalRepository, ClienteRepository clienteRepository, ExtracaoDadosUtils extracaoDadosUtils) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.clienteRepository = clienteRepository;
        this.extracaoDadosUtils = extracaoDadosUtils;
    }

    public NotaFiscal extrairDadosNotaFiscal(MultipartFile file) {
        validarArquivoPDF(file);

        try {
            NotaFiscal notaFiscal = extrairDadosNotaFiscalDoArquivo(file);
            Cliente cliente = obterClientePeloNome(notaFiscal.getCliente().getNome());
            if (cliente == null) {
                cliente = cadastrarNovoCliente(notaFiscal.getCliente());
            }
            notaFiscal.setCliente(cliente);
            return notaFiscal;
        } catch (IOException e) {
            throw new ExtracaoDadosException("Erro ao extrair dados da nota fiscal do arquivo.", e);
        }
    }

    private void validarArquivoPDF(MultipartFile file) {
        if (file == null || file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("O arquivo da nota fiscal é inválido.");
        }
    }

    private NotaFiscal extrairDadosNotaFiscalDoArquivo(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);

            String codigoBoleto = extrairCodigoBoleto(pdfText);
            LocalDate dataVencimento = extrairDataVencimento(pdfText);
            BigDecimal valor = extrairValor(pdfText);
            String beneficiario = extrairBeneficiario(pdfText);
            String nomeCliente = extrairPagador(pdfText);
            String enderecoCliente = extrairEndereco(pdfText);
            String cpfCnpjCliente = extrairCpfCnpj(pdfText);

            NotaFiscal notaFiscal = new NotaFiscal();
            notaFiscal.setCodigoBoleto(codigoBoleto);
            notaFiscal.setDataVencimento(dataVencimento);
            notaFiscal.setValor(valor);
            notaFiscal.setBeneficiario(beneficiario);

            Cliente cliente = new Cliente();
            cliente.setNome(nomeCliente);
            cliente.setEndereco(enderecoCliente);
            cliente.setCpf_Cnpj(cpfCnpjCliente);

            notaFiscal.setCliente(cliente);

            return notaFiscal;
        }
    }

    private Cliente obterClientePeloNome(String nomeCliente) {
        return clienteRepository.findByNome(nomeCliente);
    }

    private Cliente cadastrarNovoCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
