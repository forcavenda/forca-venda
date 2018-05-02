package br.com.logistica.forcavenda.models;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "regiao")
public class Regiao extends AbstractId {

  @Field("nome_regiao")
  private String nomeRegiao;

  @Field("ponto_mapa")
  private GeoJsonPoint pontoMapa;
  private int raio;

  private String pais;
  private String estado;
  private String cidade;
  private String logradouro;

  public Regiao() {

  }

  public Regiao(Regiao regiao) {
    nomeRegiao = regiao.getNomeRegiao();
    pontoMapa = regiao.getPontoMapa();
    raio = regiao.getRaio();
    pais = regiao.getPais();
    estado = regiao.getEstado();
    cidade = regiao.getCidade();
    logradouro = regiao.getLogradouro();
  }

  public String getNomeRegiao() {
    return nomeRegiao;
  }

  public void setNomeRegiao(String nomeRegiao) {
    this.nomeRegiao = nomeRegiao;
  }

  public GeoJsonPoint getPontoMapa() {
    return pontoMapa;
  }

  public void setPontoMapa(GeoJsonPoint pontoMapa) {
    this.pontoMapa = pontoMapa;
  }

  public int getRaio() {
    return raio;
  }

  public void setRaio(int raio) {
    this.raio = raio;
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
}
