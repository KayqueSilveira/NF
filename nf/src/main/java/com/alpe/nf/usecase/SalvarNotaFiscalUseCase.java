package com.alpe.nf.usecase;


import com.alpe.nf.entity.NotaFiscal;
import com.alpe.nf.adapter.repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalvarNotaFiscalUseCase {

    private final NotaFiscalRepository notaFiscalRepository;

    @Autowired
    public SalvarNotaFiscalUseCase(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    public void salvarNotaFiscal(NotaFiscal notaFiscal) {
        notaFiscalRepository.save(notaFiscal);
    }
}
