package com.alpe.nf.config;

import com.alpe.nf.entity.NotaFiscal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, NotaFiscal> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, NotaFiscal> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(NotaFiscal notaFiscal) {
        kafkaTemplate.send("notafiscal-topic", notaFiscal);
    }
}
