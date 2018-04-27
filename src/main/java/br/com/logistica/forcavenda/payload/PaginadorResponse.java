package br.com.logistica.forcavenda.payload;

import java.util.List;

public class PaginadorResponse<T> {

  private List<T> conteudo;
  private int pagina;
  private int tamanhoPagina;
  private long totalElementos;
  private int totalPaginas;
  private boolean ultimoElemento;

  public PaginadorResponse() {
  }

  public PaginadorResponse(List<T> conteudo, int pagina, int tamanhoPagina, long totalElementos,
      int totalPaginas, boolean ultimoElemento) {
    this.conteudo = conteudo;
    this.pagina = pagina;
    this.tamanhoPagina = tamanhoPagina;
    this.totalElementos = totalElementos;
    this.totalPaginas = totalPaginas;
    this.ultimoElemento = ultimoElemento;
  }

  public List<T> getConteudo() {
    return conteudo;
  }

  public void setConteudo(List<T> conteudo) {
    this.conteudo = conteudo;
  }

  public int getPagina() {
    return pagina;
  }

  public void setPagina(int pagina) {
    this.pagina = pagina;
  }

  public int getTamanhoPagina() {
    return tamanhoPagina;
  }

  public void setTamanhoPagina(int tamanhoPagina) {
    this.tamanhoPagina = tamanhoPagina;
  }

  public long getTotalElementos() {
    return totalElementos;
  }

  public void setTotalElementos(long totalElementos) {
    this.totalElementos = totalElementos;
  }

  public int getTotalPaginas() {
    return totalPaginas;
  }

  public void setTotalPaginas(int totalPaginas) {
    this.totalPaginas = totalPaginas;
  }

  public boolean isUltimoElemento() {
    return ultimoElemento;
  }

  public void setUltimoElemento(boolean ultimoElemento) {
    this.ultimoElemento = ultimoElemento;
  }
}
