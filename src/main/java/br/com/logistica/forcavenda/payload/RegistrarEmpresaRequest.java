package br.com.logistica.forcavenda.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import br.com.logistica.forcavenda.models.Empresa;
import br.com.logistica.forcavenda.models.Usuario;

public class RegistrarEmpresaRequest {

  @NotBlank(message = "Informe o CNPJ da empresa")
  private String cnpj;

  @NotNull(message = "Informe o usuário admin da empresa")
  private Usuario usuarioAdmin;

  @NotBlank(message = "Informe a razão social")
  private String razaoSocial;

  private String nomeFantasia;

  @Email
  @NotBlank(message = "Informe um email para empresa")
  private String email;

  @Email
  private String emailSecundario;

  private GeoJsonPoint pontoMapa;

  private String pais;
  private String estado;
  private String cidade;
  private String logradouro;

  @NotNull(message = "Informe a empresa matriz")
  private Empresa matriz;

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public Usuario getUsuarioAdmin() {
    return usuarioAdmin;
  }

  public void setUsuarioAdmin(Usuario usuarioAdmin) {
    this.usuarioAdmin = usuarioAdmin;
  }

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  public String getNomeFantasia() {
    return nomeFantasia;
  }

  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public Empresa getMatriz() {
    return matriz;
  }

  public void setMatriz(Empresa matriz) {
    this.matriz = matriz;
  }

}
