package br.com.logistica.forcavenda.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@Document(collection = "empresa")
public class Empresa extends AbstractId {

  @Indexed(unique = true)
  @NotBlank(message = "Informe o CNPJ da empresa")
  private String cnpj;

  @DBRef(lazy = false)
  @Field("usuario_admin")
  @NotNull(message = "Informe o usuário admin da empresa")
  private Usuario usuarioAdmin;

  @Field("razao_social")
  private String razaoSocial;

  @Field("nome_fantasia")
  private String nomeFantasia;

  @Email
  @NotBlank(message = "Informe um email para empresa")
  private String email;

  @Email
  @Field("email_secundario")
  private String emailSecundario;

  private String pais;
  private String estado;
  private String cidade;
  private String logradouro;

  @DBRef(lazy = false)
  private Empresa matriz;

  @NotNull
  @DBRef(lazy = false)
  private Regiao regiao;

  public Empresa() {

  }

  public Empresa(Empresa empresa) {
    cnpj = empresa.getCnpj();
    razaoSocial = empresa.getRazaoSocial();
    nomeFantasia = empresa.getNomeFantasia();
    usuarioAdmin = empresa.getUsuarioAdmin();
    pais = empresa.getPais();
    estado = empresa.getEstado();
    cidade = empresa.getCidade();
    logradouro = empresa.getLogradouro();
    regiao = empresa.getRegiao();
    matriz = empresa.getMatriz();
    email = empresa.getEmail();
    emailSecundario = empresa.getEmailSecundario();
  }

  public Usuario getUsuarioAdmin() {
    return usuarioAdmin;
  }

  public void setUsuarioAdmin(Usuario usuarioAdmin) {
    this.usuarioAdmin = usuarioAdmin;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
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

  public Regiao getRegiao() {
    return regiao;
  }

  public void setRegiao(Regiao regiao) {
    this.regiao = regiao;
  }

  public Empresa getMatriz() {
    return matriz;
  }

  public void setMatriz(Empresa matriz) {
    this.matriz = matriz;
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
}
