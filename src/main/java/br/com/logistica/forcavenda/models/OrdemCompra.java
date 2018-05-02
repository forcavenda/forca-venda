package br.com.logistica.forcavenda.models;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@CompoundIndexes({
    @CompoundIndex(name = "comprador_item",
        unique = true,
        def = "{'comprador.id' : 1, 'campanha_itens.id' : 1}")
})
@Document(collection = "ordem_compra")
public class OrdemCompra extends AbstractId {

  @Indexed
  @DBRef(lazy = false)
  @NotNull(message = "Informe a empresa que deseja comprar")
  private Empresa comprador;

  @Indexed
  @DBRef(lazy = false)
  @Field("campanha_itens")
  @NotNull(message = "Informe o item da campanha")
  private CampanhaItens campanhaItens;

  @Field("data_criacao")
  @Indexed(direction = IndexDirection.ASCENDING)
  private LocalDate dataCriacao;

  private double qtdeSolicitada;
}
