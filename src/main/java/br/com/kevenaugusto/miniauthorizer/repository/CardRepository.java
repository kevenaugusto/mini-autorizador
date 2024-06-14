package br.com.kevenaugusto.miniauthorizer.repository;

import br.com.kevenaugusto.miniauthorizer.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardModel, UUID> {

    Optional<CardModel> findByNumeroCartao(String numeroCarto);

}
