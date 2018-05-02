package br.com.logistica.forcavenda.models;

import java.util.Objects;

public enum StatusCampanha {

  CRIADA("CR"),
  ABERTA("AB"),
  FECHADA("FE"),
  INICIADA("IN"),
  FINALIZADA("FI"),
  CANCELADA("CA");

  private String valor;

  private StatusCampanha(String valor) {
    this.valor = valor;
  }

  public static StatusCampanha of(String value) {
    if (Objects.nonNull(value)) {
      switch (value) {
        case "AB":
          return ABERTA;
        case "FE":
          return FECHADA;
        case "IN":
          return INICIADA;
        case "FI":
          return FINALIZADA;
        case "CA":
          return CANCELADA;
      }
    }
    return CRIADA;
  }

  @Override
  public String toString() {
    if (!CRIADA.equals(this)) {
      return valor;
    }
    return null;
  }
}
