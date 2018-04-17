package br.com.logistica.forcavenda.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "papel")
public class Papel implements GrantedAuthority {
  private static final long serialVersionUID = 1L;

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  private String nome;

  public Papel(String nome) {
    super();
    this.nome = nome;
  }

  public Papel criarId() {
    id = new ObjectId();
    return this;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String getAuthority() {
    return getNome();
  }
}
