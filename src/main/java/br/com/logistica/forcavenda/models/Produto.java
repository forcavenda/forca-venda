package br.com.logistica.forcavenda.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@CompoundIndexes({
    @CompoundIndex(name = "produto_empresa",
        unique = true,
        def = "{'pertence.id' : 1, 'gtin' : 1}")
})
@Document(collection = "produto")
public class Produto extends AbstractId {

  @Indexed
  @DBRef(lazy = false)
  @NotNull(message = "Todo produto deve pertencer a uma empresa")
  private Empresa pertence;

  @Indexed
  @NotNull(message = "Informe o código de barras")
  private String gtin;

  @Field("nome_produto")
  @NotNull(message = "Informe o nome do produto")
  private String nomeProduto;

  @DBRef(lazy = false)
  @NotNull(message = "Informe a categoria do produto")
  private CategoriaProduto categoria;

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

  public CategoriaProduto getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaProduto categoria) {
    this.categoria = categoria;
  }
}
