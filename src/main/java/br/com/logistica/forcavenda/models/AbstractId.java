package br.com.logistica.forcavenda.models;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;

public abstract class AbstractId implements Auditable<Usuario, ObjectId, LocalDateTime> {

  @Id
  private ObjectId id;

  @Version
  private Long versao;

  private LocalDateTime criadoEm;
  private Usuario criadoPor;
  private LocalDateTime modificadoEm;
  private Usuario modificadoPor;

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
}
