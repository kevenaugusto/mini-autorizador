package br.com.kevenaugusto.miniauthorizer.service;

import br.com.kevenaugusto.miniauthorizer.dto.CardResponseDto;
import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExists;
import br.com.kevenaugusto.miniauthorizer.model.CardModel;
import br.com.kevenaugusto.miniauthorizer.repository.CardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public CardResponseDto createCard(InputCardDto inputCardDto) {
        Optional<CardModel> card0 = cardRepository.findByNumeroCartao(inputCardDto.numeroCartao());
        card0.ifPresent(cardModel -> {
            throw new CardAlreadyExists(cardModel.getSenha(), cardModel.getNumeroCartao());
        });
        var cardModel = new CardModel();
        BeanUtils.copyProperties(inputCardDto, cardModel);
        cardModel.setSaldo(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.DOWN));
        cardRepository.save(cardModel);
        return new CardResponseDto(cardModel.getSenha(), cardModel.getNumeroCartao());
    }

    public BigDecimal getCardBalance(String cardNumber) {
        return null;
    }

}
