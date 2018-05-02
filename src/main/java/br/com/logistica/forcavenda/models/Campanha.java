package br.com.logistica.forcavenda.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "campanha")
public class Campanha extends AbstractId {

  @DBRef(lazy = false)
  private Empresa empresa;

  @DBRef
  @Field("direcionado_para")
  private Set<Regiao> direcionadoPara = new HashSet<>();

  private String descricao;

  @Field("data_inicio")
  @Indexed(direction = IndexDirection.ASCENDING)
  private LocalDate dataInicio;
  @Field("data_termino")
  @Indexed(direction = IndexDirection.ASCENDING)
  private LocalDate dataTermino;

  @Field("qtde_minima")
  private double qtdeMinima;
  @Field("qtde_maxima")
  private double qtdeMaxima;

  @Field("status_campanha")
  private StatusCampanha statusCampanha = StatusCampanha.CRIADA;
  @Field("tipo_campanha")
  private TipoCampanha tipoCampanha = TipoCampanha.NENHUM;
  
  @DBRef(lazy = false)
  private Usuario responsavel;
  
  @Field("valor_total_previsto")
  private double valorTotalPrevisto;
  @Field("valor_total_realizado")
  private double valorTotalRealizado;
  @Field("valor_total_descontos")
  private double valorTotalDescontos;
  @Field("valor_total_frete")
  private double valorTotalFrete;
  
  public Campanha() {

  }

  public Campanha(Campanha campanha) {
    id = campanha.getId();
    empresa = campanha.getEmpresa();
    direcionadoPara = campanha.getDirecionadoPara();
  }

  public Empresa getEmpresa() {
    return empresa;
  }

  public void setEmpresa(Empresa empresa) {
    this.empresa = empresa;
  }

  public Set<Regiao> getDirecionadoPara() {
    return direcionadoPara;
  }

  public void setDirecionadoPara(Set<Regiao> direcionadoPara) {
    this.direcionadoPara = direcionadoPara;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDate getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(LocalDate dataInicio) {
    this.dataInicio = dataInicio;
  }

  public LocalDate getDataTermino() {
    return dataTermino;
  }

  public void setDataTermino(LocalDate dataTermino) {
    this.dataTermino = dataTermino;
  }

  public double getQtdeMinima() {
    return qtdeMinima;
  }

  public void setQtdeMinima(double qtdeMinima) {
    this.qtdeMinima = qtdeMinima;
  }

  public double getQtdeMaxima() {
    return qtdeMaxima;
  }

  public void setQtdeMaxima(double qtdeMaxima) {
    this.qtdeMaxima = qtdeMaxima;
  }

  public StatusCampanha getStatusCampanha() {
    return statusCampanha;
  }

  public void setStatusCampanha(StatusCampanha statusCampanha) {
    this.statusCampanha = statusCampanha;
  }

  public TipoCampanha getTipoCampanha() {
    return tipoCampanha;
  }

  public void setTipoCampanha(TipoCampanha tipoCampanha) {
    this.tipoCampanha = tipoCampanha;
  }

  public Usuario getResponsavel() {
    return responsavel;
  }

  public void setResponsavel(Usuario responsavel) {
    this.responsavel = responsavel;
  }

  public double getValorTotalPrevisto() {
    return valorTotalPrevisto;
  }

  public void setValorTotalPrevisto(double valorTotalPrevisto) {
    this.valorTotalPrevisto = valorTotalPrevisto;
  }

  public double getValorTotalRealizado() {
    return valorTotalRealizado;
  }

  public void setValorTotalRealizado(double valorTotalRealizado) {
    this.valorTotalRealizado = valorTotalRealizado;
  }

  public double getValorTotalDescontos() {
    return valorTotalDescontos;
  }

  public void setValorTotalDescontos(double valorTotalDescontos) {
    this.valorTotalDescontos = valorTotalDescontos;
  }

  public double getValorTotalFrete() {
    return valorTotalFrete;
  }

  public void setValorTotalFrete(double valorTotalFrete) {
    this.valorTotalFrete = valorTotalFrete;
  }

}
