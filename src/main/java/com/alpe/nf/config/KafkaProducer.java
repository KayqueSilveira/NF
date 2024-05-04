package com.alpe.nf.config;

import com.alpe.nf.entity.NotaFiscal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, NotaFiscal> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, NotaFiscal> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(NotaFiscal notaFiscal) {
        CompletableFuture<SendResult<String, NotaFiscal>> future = kafkaTemplate.send("notafiscal-topic", notaFiscal);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error("Falha ao enviar mensagem para o tópico: ", ex);
            } else {
                LOGGER.info("Mensagem enviada com sucesso para o tópico: " + result.getRecordMetadata().topic());
            }
        });
    }
}
