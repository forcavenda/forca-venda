package br.com.logistica.forcavenda.models;

import java.util.Objects;

public enum TipoCampanha {
  NENHUM("NE"),
  EMPURRADA("EM"),
  PUXADA("PU");

  private String valor;

  private TipoCampanha(String valor) {
    this.valor = valor;
  }

  public static TipoCampanha of(String value) {
    if (Objects.nonNull(value)) {
      switch (value) {
        case "EM":
          return EMPURRADA;
        case "PU":
          return PUXADA;
      }
    }
    return NENHUM;
  }

  @Override
  public String toString() {
    if (!NENHUM.equals(this)) {
      return valor;
    }
    return null;
  }
}
