package br.com.logistica.forcavenda.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Campanha;

@Repository
public interface CampanhaRepository extends MongoRepository<Campanha, String> {

}
