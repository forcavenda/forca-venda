package br.com.logistica.forcavenda.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Papel;

@Repository
public interface PapelRepository extends MongoRepository<Papel, String> {

  Optional<Papel> findByNome(String nome);
}
