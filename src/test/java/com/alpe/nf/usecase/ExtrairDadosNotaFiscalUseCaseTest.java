package com.alpe.nf.usecase;

import com.alpe.nf.adapter.repository.ClienteRepository;
import com.alpe.nf.adapter.repository.NotaFiscalRepository;
import com.alpe.nf.adapter.util.ExtracaoDadosUtils;
import com.alpe.nf.entity.Cliente;
import com.alpe.nf.entity.NotaFiscal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ExtrairDadosNotaFiscalUseCaseTest {

    @InjectMocks
    private ExtrairDadosNotaFiscalUseCase extrairDadosNotaFiscalUseCase;

    @Mock
    private NotaFiscalRepository notaFiscalRepository;

    @Mock
    private ExtracaoDadosUtils extracaoDadosUtils;

    @Mock
    private ClienteRepository clienteRepository;

    public ExtrairDadosNotaFiscalUseCaseTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("quando passar os dados do boleto deve extrari corretamente para um objeto do tipo NotaFiscal")
    void extrairDadosNotaFiscalDoArquivo() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        MultipartFile file = getMultiFilePDF();
        NotaFiscal notaFiscal = getNotaFiscal();

        Cliente cliente = getCliente();

        notaFiscal.setCliente(cliente);

        NotaFiscal result = extrairDadosNotaFiscalUseCase.extrairDadosNotaFiscalDoArquivo(file);

        assertEquals(notaFiscal, result);
    }

    private static NotaFiscal getNotaFiscal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataVencimento = LocalDate.parse("30/04/2024", formatter);
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setCodigoBoleto("2");
        notaFiscal.setDataVencimento(dataVencimento);
        notaFiscal.setValor(BigDecimal.valueOf(1298.64));
        notaFiscal.setBeneficiario("Banco teste");
        return notaFiscal;
    }

    private static Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("cliente x");
        cliente.setEndereco("Rua x");
        cliente.setCpf_Cnpj("000.000.000-00");
        return cliente;
    }

    @Test
    @DisplayName("quando inserir arquivo que não seja PDF deve retornar exception")
    public void validarArquivoPDF_Invalid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "txt content".getBytes());

        Method method = getMethodValidarArquivoPDF();

        try {
            method.invoke(extrairDadosNotaFiscalUseCase, file);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            assertTrue(cause instanceof IllegalArgumentException);
            assertEquals("O arquivo da nota fiscal é inválido.", cause.getMessage());
        }
    }

    @Test
    @DisplayName("quando arquivo for PDF deve validar o boleto corretamente")
    public void validarArquivoPDF_Valid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "application/pdf", "pdf content".getBytes());

        Method method = getMethodValidarArquivoPDF();

        assertDoesNotThrow(() -> method.invoke(extrairDadosNotaFiscalUseCase, file));
    }

    private Method getMethodValidarArquivoPDF() throws NoSuchMethodException {
        Method method = ExtrairDadosNotaFiscalUseCase.class.getDeclaredMethod("validarArquivoPDF", MultipartFile.class);
        method.setAccessible(true);
        return method;
    }

    private Method getMethodextrairDadosNotaFiscalDoArquivo(MultipartFile file) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = ExtrairDadosNotaFiscalUseCase.class;

        Method method = clazz.getDeclaredMethod("extrairDadosNotaFiscalDoArquivo", MultipartFile.class);
        method.setAccessible(true);

        ExtrairDadosNotaFiscalUseCase instance = new ExtrairDadosNotaFiscalUseCase(notaFiscalRepository, clienteRepository, extracaoDadosUtils);

        method.invoke(instance, file);

        return method;
    }

    private MultipartFile getMultiFilePDF() throws IOException {
        String filePath = "C:\\Users\\kayqu\\OneDrive\\Documentos\\workspace\\NF\\Boleto.pdf";
        File file = new File(filePath);

        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "application/pdf", input);
        return multipartFile;
    }
}
