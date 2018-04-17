package br.com.logistica.forcavenda.models;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "usuario")
public class Usuario extends AbstractId {

  @Size(max = 100)
  private String nome;

  @NotBlank(message = "Informe um nome de usuário")
  @Size(min = 10, max = 20)
  @Field("nome_usuario")
  @Indexed(unique = true)
  private String nomeUsuario;

  @NotBlank(message = "Informe um e-mail")
  private String email;

  @NotBlank(message = "Informe a senha")
  private String senha;

  private String habilitado = "N";

  @Transient
  private String confirmeSenha;

  @DBRef
  private Usuario supervisor;

  @DBRef(lazy = false)
  @NotEmpty(message = "Usuário deve possuir papeis")
  private List<Papel> papeis;

  public Usuario() {
  }

  public Usuario(Usuario usuario) {
    nomeUsuario = usuario.getNomeUsuario();
    nome = usuario.getNome();
    email = usuario.getEmail();
    senha = usuario.getSenha();
    papeis = usuario.getPapeis();
    habilitado = usuario.getHabilitado();
    supervisor = usuario.getSupervisor();
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

  public String getConfirmeSenha() {
    return confirmeSenha;
  }

  public void setConfirmeSenha(String confirmeSenha) {
    this.confirmeSenha = confirmeSenha;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public List<Papel> getPapeis() {
    return papeis;
  }

  public void setPapeis(List<Papel> papeis) {
    this.papeis = papeis;
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
}
