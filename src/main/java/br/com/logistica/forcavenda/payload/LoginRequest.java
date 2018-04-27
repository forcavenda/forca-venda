package br.com.logistica.forcavenda.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

  @NotBlank
  private String nomeUsuario;
  @NotBlank
  private String senha;

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
}
