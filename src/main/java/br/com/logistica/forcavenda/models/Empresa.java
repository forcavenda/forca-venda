package br.com.logistica.forcavenda.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "empresa")
public class Empresa extends AbstractId {

  @Indexed(unique = true)
  private String cnpj;

  @Field("razao_social")
  private String razaoSocial;

  @Field("nome_fantasia")
  private String nomeFantasia;

  private String pais;
  private String estado;
  private String cidade;
  private String logradouro;

  @DBRef
  @NotNull
  private Regiao regiao;

  public Empresa() {

  }

  public Empresa(Empresa empresa) {
    cnpj = empresa.getCnpj();
    razaoSocial = empresa.getRazaoSocial();
    nomeFantasia = empresa.getNomeFantasia();
    pais = empresa.getPais();
    estado = empresa.getEstado();
    cidade = empresa.getCidade();
    logradouro = empresa.getLogradouro();
    regiao = empresa.getRegiao();
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  public String getNomeFantasia() {
    return nomeFantasia;
  }

  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public Regiao getRegiao() {
    return regiao;
  }

  public void setRegiao(Regiao regiao) {
    this.regiao = regiao;
  }
}
