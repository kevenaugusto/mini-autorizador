package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.dto.TransactionDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transacoes")
    public ResponseEntity<TransactionStatus> doTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.doTransaction(transactionDto));
    }

}
