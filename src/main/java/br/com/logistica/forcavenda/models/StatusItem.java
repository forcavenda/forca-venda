package br.com.logistica.forcavenda.models;

import java.util.Objects;

public enum StatusItem {

  NENHUM("NE"),
  CONFIRMADO("CO"),
  CANCELADO("CA");

  private String valor;

  private StatusItem(String valor) {
    this.valor = valor;
  }

  public static StatusItem of(String value) {
    if (Objects.nonNull(value)) {
      switch (value) {
        case "CO":
          return CONFIRMADO;
        case "CA":
          return CANCELADO;
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
