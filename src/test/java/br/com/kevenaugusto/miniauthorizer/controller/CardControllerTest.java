package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.builder.CardBuilder;
import br.com.kevenaugusto.miniauthorizer.exception.CardAlreadyExistsException;
import br.com.kevenaugusto.miniauthorizer.exception.CardNotFoundException;
import br.com.kevenaugusto.miniauthorizer.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    private static final String ENDPOINT = "/cartoes";
    private static final String APPLICATION_JSON = "application/json";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String INVALID_CARD_NUMBER = "0000000000000000";

    @Test
    void shouldReturnStatus201WhenCardCreated() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildValidCard())))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenCardNumberIsNull() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildCardWithInvalidCardNumber())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsNull() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildCardWithInvalidPassword())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowCardAlreadyExistsException() throws Exception {
        when(cardService.createCard(CardBuilder.buildDuplicatedCard())).thenThrow(CardAlreadyExistsException.class);
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildDuplicatedCard())))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldReturnUnauthorizedWhenUserIsWrong() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic("invalidUser", PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildCardWithInvalidCardNumber())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenPasswordIsWrong() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, "invalidPassword"))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CardBuilder.buildCardWithInvalidCardNumber())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnOkWhenCardExists() throws Exception {
        var existentCard = CardBuilder.buildExistentCard();
        when(cardService.getCardBalance(existentCard.getNumeroCartao())).thenReturn(existentCard.getSaldo());
        mockMvc
            .perform(get(ENDPOINT + "/{cardNumber}", existentCard.getNumeroCartao())
                .with(httpBasic(USERNAME, PASSWORD))
                .accept(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenCardDoesNotExist() throws Exception {
        when(cardService.getCardBalance(INVALID_CARD_NUMBER)).thenThrow(CardNotFoundException.class);
        mockMvc
                .perform(get(ENDPOINT + "/{cardNumber}", INVALID_CARD_NUMBER)
                        .with(httpBasic(USERNAME, PASSWORD))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
