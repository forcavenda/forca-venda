package br.com.logistica.forcavenda.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "categoria_produto")
public class CategoriaProduto {

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  private String categoria;

  @DBRef(lazy = false)
  @Field("categoria_pai")
  private CategoriaProduto categoriaPai;

  public CategoriaProduto() {
  }

  public CategoriaProduto(String categoria) {
    this.categoria = categoria;
  }

  public CategoriaProduto criarId() {
    id = new ObjectId();
    return this;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public CategoriaProduto getCategoriaPai() {
    return categoriaPai;
  }

  public void setCategoriaPai(CategoriaProduto categoriaPai) {
    this.categoriaPai = categoriaPai;
  }
}
