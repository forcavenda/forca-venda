package br.com.logistica.forcavenda.repositories;

import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Empresa;

@Repository
public interface EmpresaRepository extends IRepository<Empresa, String> {

}
