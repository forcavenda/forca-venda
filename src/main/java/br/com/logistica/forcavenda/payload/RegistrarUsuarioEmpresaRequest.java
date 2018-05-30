package br.com.logistica.forcavenda.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class RegistrarUsuarioEmpresaRequest {

  @Size(max = 100)
  @NotBlank(message = "Informe a razão social")
  private String razaoSocial;

  @Email
  @NotBlank(message = "Informe um e-mail")
  private String email;

  @Size(min = 14, max = 14)
  @NotBlank(message = "Informe o CNPJ da empresa")
  private String cnpj;

  @Size(min = 10, max = 20)
  @NotBlank(message = "Informe a senha")
  private String senha;

  private String confirmeSenha;
  private String nomeFantasia;

  @Email
  private String emailSecundario;
  private GeoJsonPoint pontoMapa;

  private String pais;
  private String estado;
  private String cidade;
  private String logradouro;

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
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

  public String getNomeFantasia() {
    return nomeFantasia;
  }

  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }

  public String getEmailSecundario() {
    return emailSecundario;
  }

  public void setEmailSecundario(String emailSecundario) {
    this.emailSecundario = emailSecundario;
  }

  public GeoJsonPoint getPontoMapa() {
    return pontoMapa;
  }

  public void setPontoMapa(GeoJsonPoint pontoMapa) {
    this.pontoMapa = pontoMapa;
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
