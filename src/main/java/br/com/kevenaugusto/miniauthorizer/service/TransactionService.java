package br.com.kevenaugusto.miniauthorizer.service;

import br.com.kevenaugusto.miniauthorizer.dto.TransactionDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    public TransactionStatus doTransaction(TransactionDto transactionDto) {
        var card0 = cardRepository.findByNumeroCartao(transactionDto.numeroCartao()).orElseThrow(() -> new TransactionException(TransactionStatus.CARTAO_INEXISTENTE));
        Optional.ofNullable(card0.getSenha().equals(transactionDto.senhaCartao()) ? true : null).orElseThrow(() -> new TransactionException(TransactionStatus.SENHA_INVALIDA));
        Optional.ofNullable(card0.getSaldo().compareTo(transactionDto.valor()) >= 0 ? true : null).orElseThrow(() -> new TransactionException(TransactionStatus.SALDO_INSUFICIENTE));
        card0.setSaldo(card0.getSaldo().subtract(transactionDto.valor()));
        cardRepository.save(card0);
        return TransactionStatus.OK;
    }

}
