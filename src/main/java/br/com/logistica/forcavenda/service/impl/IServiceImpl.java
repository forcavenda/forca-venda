package br.com.logistica.forcavenda.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.logistica.forcavenda.service.IService;

@Transactional(readOnly = true)
public class IServiceImpl<T, ID extends Serializable> implements IService<T, ID> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private MongoRepository<T, ID> repository;

  public Logger getLogger() {
    return logger;
  }

  public MongoRepository<T, ID> getRepository() {
    return repository;
  }

  @Override
  @Transactional
  public <S extends T> S update(S entity) {
    logger.debug("Atualizando documento {} com informação: {}", entity.getClass(), entity
      .toString());
    return getRepository().save(entity);
  }

  @Override
  public Iterable<T> findAll(int page, int size) {
    logger.debug("Carregando documento pagina {} tamanho: {}", page, size);
    return getRepository().findAll(PageRequest.of(page, size));
  }

  @Override
  public List<T> findAll() {
    return getRepository().findAll();
  }

  @Override
  @Transactional
  public void deleteById(ID id) {
    logger.debug("Excluindo documento id {}", id);
    getRepository().deleteById(id);
  }

  @Override
  @Transactional
  public void delete(T entity) {
    logger.debug("Excluindo documento {} com informação: {}", entity.getClass(), entity.toString());
    getRepository().delete(entity);
  }

  @Override
  @Transactional
  public <S extends T> S insert(S entity) {
    logger.debug("Criando documento {} com informação: {}", entity.getClass(), entity.toString());
    return getRepository().insert(entity);
  }

  @Override
  public Optional<T> findById(ID id) {
    logger.debug("Buscando documento id {}", id);
    return getRepository().findById(id);
  }

  @Override
  public boolean exists(ID id) {
    logger.debug("Verificando documento id {}", id);
    return getRepository().existsById(id);
  }

}
