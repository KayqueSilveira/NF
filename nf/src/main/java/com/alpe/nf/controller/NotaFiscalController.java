package com.alpe.nf.controller;


import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/nota-fiscal")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalService notaFiscalService;

    @PostMapping("/upload")
    public ResponseEntity<?> processarNotaFiscal(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("O arquivo da nota fiscal está vazio.");
        }

        NotaFiscal notaFiscal = notaFiscalService.processarNotaFiscal(file);
        return ResponseEntity.ok(notaFiscal);
    }
}

