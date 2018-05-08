package br.com.logistica.forcavenda.models;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
@Document(collection = "usuario")
public class Usuario extends AbstractId {

  @Size(max = 100)
  private String nome;

  @Size(min = 10, max = 20)
  @NotBlank(message = "Informe um nome de usuário")
  @Field("nome_usuario")
  @Indexed(unique = true)
  private String nomeUsuario;

  @Email
  @NotBlank(message = "Informe um e-mail")
  private String email;

  @JsonIgnore
  @Size(min = 10, max = 20)
  @NotBlank(message = "Informe a senha")
  private String senha;

  private String habilitado = "N";

  @DBRef
  private Usuario supervisor;

  @DBRef(lazy = false)
  @NotEmpty(message = "Usuário deve possuir papeis")
  private Set<Papel> papeis = new HashSet<>();

  @DBRef(lazy = false)
  private Set<Empresa> acessos = new HashSet<>();

  public Usuario() {
  }

  public Usuario(Usuario usuario) {
    id = usuario.getId();
    nomeUsuario = usuario.getNomeUsuario();
    acessos = usuario.getAcessos();
    nome = usuario.getNome();
    email = usuario.getEmail();
    senha = usuario.getSenha();
    papeis = usuario.getPapeis();
    habilitado = usuario.getHabilitado();
    supervisor = usuario.getSupervisor();
  }

  public Set<Empresa> getAcessos() {
    return acessos;
  }

  public void setAcessos(Set<Empresa> acessos) {
    this.acessos = acessos;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getHabilitado() {
    return habilitado;
  }

  public void setHabilitado(String habilitado) {
    this.habilitado = habilitado;
  }

  public Usuario getSupervisor() {
    return supervisor;
  }

  public void setSupervisor(Usuario supervisor) {
    this.supervisor = supervisor;
  }

  public Set<Papel> getPapeis() {
    return papeis;
  }

  public void setPapeis(Set<Papel> papeis) {
    this.papeis = papeis;
  }


}
