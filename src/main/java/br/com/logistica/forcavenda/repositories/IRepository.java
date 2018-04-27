package br.com.logistica.forcavenda.repositories;

import java.io.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

}
