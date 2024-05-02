package com.alpe.nf.application.config;

import com.alpe.nf.entity.NotaFiscal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(NotaFiscal notaFiscal) {
        kafkaTemplate.send("notafiscal-topic", notaFiscal);
    }
}
