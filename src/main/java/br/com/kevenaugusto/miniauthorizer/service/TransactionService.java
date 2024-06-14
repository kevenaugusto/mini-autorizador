package br.com.kevenaugusto.miniauthorizer.service;

import br.com.kevenaugusto.miniauthorizer.dto.TransactionDto;
import br.com.kevenaugusto.miniauthorizer.enumeration.TransactionStatus;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.repository.CardRepository;
import br.com.kevenaugusto.miniauthorizer.singleton.TransactionMap;
import br.com.kevenaugusto.miniauthorizer.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionStatus doTransaction(TransactionDto transactionDto) {
        var transactionMap = TransactionMap.getInstance();
        while (!transactionMap.lockCard(transactionDto.numeroCartao())) {
            logger.info("There is a pending transaction for card: {}", transactionDto.numeroCartao());
            ThreadUtils.sleepForOneSecond();
        }
        var card0 = cardRepository.findByNumeroCartao(transactionDto.numeroCartao()).orElseThrow(() -> new TransactionException(TransactionStatus.CARTAO_INEXISTENTE, transactionDto.numeroCartao()));
        Optional.ofNullable(card0.getSenha().equals(transactionDto.senhaCartao()) ? true : null).orElseThrow(() -> new TransactionException(TransactionStatus.SENHA_INVALIDA, transactionDto.numeroCartao()));
        Optional.ofNullable(card0.getSaldo().compareTo(transactionDto.valor()) >= 0 ? true : null).orElseThrow(() -> new TransactionException(TransactionStatus.SALDO_INSUFICIENTE, transactionDto.numeroCartao()));
        card0.setSaldo(card0.getSaldo().subtract(transactionDto.valor()));
        cardRepository.save(card0);
        transactionMap.unlockCard(transactionDto.numeroCartao());
        return TransactionStatus.OK;
    }

}
