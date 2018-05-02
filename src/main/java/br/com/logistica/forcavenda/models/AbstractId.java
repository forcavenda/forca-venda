package br.com.logistica.forcavenda.models;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;

import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class AbstractId implements Auditable<Usuario, ObjectId, LocalDateTime> {

  @Id
  protected ObjectId id;

  @Version
  protected Long versao;

  @JsonIgnore
  protected LocalDateTime criadoEm;
  @JsonIgnore
  protected Usuario criadoPor;
  @JsonIgnore
  protected LocalDateTime modificadoEm;
  @JsonIgnore
  protected Usuario modificadoPor;

  public AbstractId criarId() {
    id = new ObjectId();
    return this;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return id == null;
  }

  @Override
  public Optional<Usuario> getCreatedBy() {
    return Optional.of(criadoPor);
  }

  @Override
  public void setCreatedBy(Usuario createdBy) {
    criadoPor = createdBy;
  }

  @Override
  public Optional<LocalDateTime> getCreatedDate() {
    return Optional.of(criadoEm);
  }

  @Override
  public void setCreatedDate(LocalDateTime creationDate) {
    criadoEm = creationDate;
  }

  @Override
  public Optional<Usuario> getLastModifiedBy() {
    return Optional.of(modificadoPor);
  }

  @Override
  public void setLastModifiedBy(Usuario lastModifiedBy) {
    modificadoPor = lastModifiedBy;
  }

  @Override
  public Optional<LocalDateTime> getLastModifiedDate() {
    return Optional.of(modificadoEm);
  }

  @Override
  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    modificadoEm = lastModifiedDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (id == null ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractId other = (AbstractId) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
