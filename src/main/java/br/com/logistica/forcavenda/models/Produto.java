package br.com.logistica.forcavenda.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "produto")
public class Produto extends AbstractId {

  @DBRef
  @NotNull(message = "Todo produto deve pertencer a uma empresa")
  private Empresa pertence;

  @Indexed(unique = true)
  @NotNull(message = "Informe o código de barras")
  private String gtin;

  @Field("nome_produto")
  private String nomeProduto;

  @Field("path_foto")
  private String pathFoto;

  public Produto() {

  }

  public Produto(Produto produto) {
    pertence = produto.getPertence();
    gtin = produto.getGtin();
    nomeProduto = produto.getNomeProduto();
    pathFoto = produto.getPathFoto();
  }

  public Empresa getPertence() {
    return pertence;
  }

  public void setPertence(Empresa pertence) {
    this.pertence = pertence;
  }

  public String getGtin() {
    return gtin;
  }

  public void setGtin(String gtin) {
    this.gtin = gtin;
  }

  public String getNomeProduto() {
    return nomeProduto;
  }

  public void setNomeProduto(String nomeProduto) {
    this.nomeProduto = nomeProduto;
  }

  public String getPathFoto() {
    return pathFoto;
  }

  public void setPathFoto(String pathFoto) {
    this.pathFoto = pathFoto;
  }
}
