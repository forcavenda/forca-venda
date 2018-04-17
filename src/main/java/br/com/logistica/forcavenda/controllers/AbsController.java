package br.com.logistica.forcavenda.controllers;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.logistica.forcavenda.service.IService;

public abstract class AbsController<T, ID extends Serializable> {

  protected IService<T, ID> services;

  public AbsController(IService<T, ID> services) {
    super();
    this.services = services;
  }

  @GetMapping("/all")
  public List<T> getAll() {
    return services.findAll();
  }

  @PostMapping("/create")
  public T create(@Valid @RequestBody T resource) {
    return services.insert(resource);
  }

  @GetMapping(value = "/get/{id}")
  public ResponseEntity<T> getById(@PathVariable("id") ID id) {
    return services.findById(id).map(entity -> ResponseEntity.ok().body(entity)).orElse(
      ResponseEntity.notFound().build());
  }

  @PutMapping(value = "/update/{id}")
  public ResponseEntity<T> update(@PathVariable("id") ID id,
      @Valid @RequestBody T resource) {
    return services.findById(id)
      .map(entity -> {
        BeanUtils.copyProperties(entity, resource);
        T merge = services.update(entity);
        return ResponseEntity.ok().body(merge);
      }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") ID id) {
    return services.findById(id)
      .map(entity -> {
        services.deleteById(id);
        return ResponseEntity.ok().build();
      }).orElse(ResponseEntity.notFound().build());
  }
}
