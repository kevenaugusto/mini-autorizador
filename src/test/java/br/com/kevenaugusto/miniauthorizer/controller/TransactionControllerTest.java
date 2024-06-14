package br.com.kevenaugusto.miniauthorizer.controller;

import br.com.kevenaugusto.miniauthorizer.builder.TransactionBuilder;
import br.com.kevenaugusto.miniauthorizer.exception.TransactionException;
import br.com.kevenaugusto.miniauthorizer.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    private static final String ENDPOINT = "/transacoes";
    private static final String APPLICATION_JSON = "application/json";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Test
    void shouldReturnCreatedAndTransactionIsSuccessful() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildValidTransaction())))
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
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInvalidCardNumber())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCardNumberIsAlphanumeric() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithAlphanumericCardNumber())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCardNumberSizeIsLessThanMinimum() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithCardNumberLessThanMinimum())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCardNumberSizeIsGreaterThanMaximum() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithCardNumberGreaterThanMaximum())))
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
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInvalidPassword())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenValueIsNull() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInvalidValue())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedWhenUserIsWrong() throws Exception {
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic("invalidUser", PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInvalidCardNumber())))
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
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInvalidCardNumber())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldThrowTransactionExceptionWhenInsufficientBalance() throws Exception {
        doThrow(TransactionException.class).when(transactionService).doTransaction(TransactionBuilder.buildTransactionWithInsufficientBalance());
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInsufficientBalance())))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldThrowTransactionExceptionWhenInvalidPassword() throws Exception {
        doThrow(TransactionException.class).when(transactionService).doTransaction(TransactionBuilder.buildTransactionWithWrongPassword());
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithWrongPassword())))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldThrowTransactionExceptionWhenCardNotFound() throws Exception {
        doThrow(TransactionException.class).when(transactionService).doTransaction(TransactionBuilder.buildTransactionWithInexistentCardNumber());
        mockMvc
            .perform(post(ENDPOINT)
                .with(csrf())
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TransactionBuilder.buildTransactionWithInexistentCardNumber())))
            .andExpect(status().isUnprocessableEntity());
    }

}
