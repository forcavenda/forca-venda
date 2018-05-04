package br.com.logistica.forcavenda.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Empresa;
import br.com.logistica.forcavenda.models.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

  @Query("{ 'pertence' : ?0 }")
  Page<Produto> findByPertence(Empresa pertence, Pageable pageable);
}
