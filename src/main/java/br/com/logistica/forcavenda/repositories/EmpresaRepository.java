package br.com.logistica.forcavenda.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Empresa;

@Repository
public interface EmpresaRepository extends MongoRepository<Empresa, String> {

}
