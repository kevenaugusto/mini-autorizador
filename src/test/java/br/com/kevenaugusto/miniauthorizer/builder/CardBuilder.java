package br.com.kevenaugusto.miniauthorizer.builder;

import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;
import br.com.kevenaugusto.miniauthorizer.model.CardModel;

import java.math.BigDecimal;
import java.util.UUID;

public class CardBuilder {

    public static InputCardDto buildValidCard() {
        return new InputCardDto("6549873025634501", "1234");
    }

    public static InputCardDto buildCardWithInvalidCardNumber() {
        return new InputCardDto(null, "5678");
    }

    public static InputCardDto buildCardWithInvalidPassword() {
        return new InputCardDto("6549873025634502", null);
    }

    public static InputCardDto buildDuplicatedCard() {
        return buildValidCard();
    }

    public static CardModel buildExistentCard() {
        var cardModel = new CardModel();
        cardModel.setCardId(UUID.randomUUID());
        cardModel.setNumeroCartao("6549873025634503");
        cardModel.setSenha("9012");
        cardModel.setSaldo(BigDecimal.valueOf(500.00));
        return cardModel;
    }

}
