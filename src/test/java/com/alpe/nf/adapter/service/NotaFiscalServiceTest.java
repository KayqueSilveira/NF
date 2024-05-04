package com.alpe.nf.adapter.service;

import com.alpe.nf.config.KafkaProducer;
import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.usecase.ExtrairDadosNotaFiscalUseCase;
import com.alpe.nf.usecase.SalvarNotaFiscalUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotaFiscalServiceTest {

    @Mock
    private ExtrairDadosNotaFiscalUseCase extrairDadosNotaFiscalUseCase;

    @Mock
    private SalvarNotaFiscalUseCase salvarNotaFiscalUseCase;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private CobrancaService cobrancaService;

    @Mock
    private MultipartFile multipartFile;

    private NotaFiscalService notaFiscalService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notaFiscalService = new NotaFiscalService(extrairDadosNotaFiscalUseCase, salvarNotaFiscalUseCase, cobrancaService, kafkaProducer);
    }

    @Test
    @DisplayName("quando passar os dados corretamente deve produzir a mensagem no topico do kafka e enviar para o ms de cobrança")
    public void testProcessarNotaFiscal() {
        NotaFiscal notaFiscalMock = new NotaFiscal();
        when(extrairDadosNotaFiscalUseCase.extrairDadosNotaFiscal(multipartFile)).thenReturn(notaFiscalMock);
        doNothing().when(salvarNotaFiscalUseCase).salvarNotaFiscal(notaFiscalMock);
        doNothing().when(kafkaProducer).enviarMensagem(notaFiscalMock);

        NotaFiscal resultado = notaFiscalService.processarNotaFiscal(multipartFile);

        verify(extrairDadosNotaFiscalUseCase, times(1)).extrairDadosNotaFiscal(multipartFile);
        verify(salvarNotaFiscalUseCase, times(1)).salvarNotaFiscal(notaFiscalMock);
        verify(kafkaProducer, times(1)).enviarMensagem(notaFiscalMock);

        assertEquals(notaFiscalMock, resultado);

        verify(cobrancaService, times(1)).processarCobranca(notaFiscalMock);
    }


}