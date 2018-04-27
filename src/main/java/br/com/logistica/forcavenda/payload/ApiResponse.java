package br.com.logistica.forcavenda.payload;

public class ApiResponse {
  private Boolean sucesso;
  private String mensagem;

  public ApiResponse(Boolean sucesso, String mensagem) {
    this.sucesso = sucesso;
    this.mensagem = mensagem;
  }

  public Boolean getSucesso() {
    return sucesso;
  }

  public void setSucesso(Boolean sucesso) {
    this.sucesso = sucesso;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
}
