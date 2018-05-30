package br.com.logistica.forcavenda.security;

public enum Authority {
  ROLE_ADMIN(0),
  ROLE_EMPRESA(1),
  ROLE_USUARIO(2),
  REFRESH_TOKEN(10);

  private int code;

  Authority(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static Authority parse(String value) {
    Authority authority = null;
    if (value != null && value.length() != 0) {
      for (Authority current : Authority.values()) {
        if (current.name().equalsIgnoreCase(value)) {
          authority = current;
          break;
        }
      }
    }
    return authority;
  }
}
