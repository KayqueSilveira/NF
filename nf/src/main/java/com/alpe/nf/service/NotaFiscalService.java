package com.alpe.nf.service;


import com.alpe.nf.application.config.KafkaProducer;
import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.service.usecase.ExtrairDadosNotaFiscalUseCase;
import com.alpe.nf.service.usecase.SalvarNotaFiscalUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class NotaFiscalService {

    private final ExtrairDadosNotaFiscalUseCase extrairDadosNotaFiscalUseCase;
    private final SalvarNotaFiscalUseCase salvarNotaFiscalUseCase;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public NotaFiscalService(ExtrairDadosNotaFiscalUseCase extrairDadosNotaFiscalUseCase, SalvarNotaFiscalUseCase salvarNotaFiscalUseCase, KafkaProducer kafkaProducer) {
        this.extrairDadosNotaFiscalUseCase = extrairDadosNotaFiscalUseCase;
        this.salvarNotaFiscalUseCase = salvarNotaFiscalUseCase;
        this.kafkaProducer = kafkaProducer;
    }

    public NotaFiscal processarNotaFiscal(MultipartFile file) {
        NotaFiscal notaFiscal = extrairDadosNotaFiscalUseCase.extrairDadosNotaFiscal(file);
        salvarNotaFiscalUseCase.salvarNotaFiscal(notaFiscal);
        kafkaProducer.enviarMensagem(notaFiscal);
        return notaFiscal;
    }
}
