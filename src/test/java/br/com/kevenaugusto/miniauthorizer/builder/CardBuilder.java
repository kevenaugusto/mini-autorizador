package br.com.kevenaugusto.miniauthorizer.builder;

import br.com.kevenaugusto.miniauthorizer.dto.InputCardDto;

public class CardBuilder {

    public static InputCardDto buildValidCard() {
        return new InputCardDto("6549873025634501", "1234");
    }

    public static InputCardDto buildCardWithInvalidCardNumber() {
        return new InputCardDto(null, "1234");
    }

    public static InputCardDto buildCardWithInvalidPassword() {
        return new InputCardDto("6549873025634501", null);
    }

    public static InputCardDto buildDuplicatedCard() {
        return buildValidCard();
    }

}
