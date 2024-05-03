package com.alpe.nf.adapter.controller;

import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.adapter.service.NotaFiscalService;
import com.alpe.nf.exception.InvalidFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("private")
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    public NotaFiscalController(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }

    @PostMapping("nota-fiscal/upload")
    public ResponseEntity<?> processarNotaFiscal(@RequestParam("file") MultipartFile file) {
        try {
            NotaFiscal notaFiscal = notaFiscalService.processarNotaFiscal(file);
            return ResponseEntity.ok(notaFiscal);
        } catch (InvalidFileException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar a nota fiscal: " + e.getMessage());
        }
    }
}

