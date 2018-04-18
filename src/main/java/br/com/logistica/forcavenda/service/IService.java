package br.com.logistica.forcavenda.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IService<T, ID extends Serializable> {
  <S extends T> S insert(S entity);

  <S extends T> S update(S entity);

  List<T> findAll();

  void deleteById(ID id);

  void delete(T entity);

  Optional<T> findById(ID id);

  boolean exists(ID id);

  Iterable<T> findAll(int page, int size);
}
