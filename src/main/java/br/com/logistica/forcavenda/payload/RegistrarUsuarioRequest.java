package br.com.logistica.forcavenda.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrarUsuarioRequest {

  @Size(max = 100)
  private String nome;

  @Email
  @NotBlank(message = "Informe um e-mail")
  private String email;

  @Size(min = 10, max = 20)
  @NotBlank(message = "Informe um nome de usuário")
  private String nomeUsuario;

  @Size(min = 10, max = 20)
  @NotBlank(message = "Informe a senha")
  private String senha;

  private String confirmeSenha;

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

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
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
}
