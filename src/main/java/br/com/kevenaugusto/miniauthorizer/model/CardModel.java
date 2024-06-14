package br.com.kevenaugusto.miniauthorizer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "CARD_DETAILS")
@Getter
@Setter
public class CardModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cardId;
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;

}
