package br.com.logistica.forcavenda.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.ToString;

@ToString
@CompoundIndexes({
    @CompoundIndex(name = "produto_campanha",
        unique = true,
        def = "{'campanha.id' : 1, 'produto.id' : 1}")
})
@Document(collection = "campanha_itens")
public class CampanhaItens extends AbstractId {

  @Indexed
  @DBRef(lazy = false)
  @NotNull(message = "Informe a campanha")
  private Campanha campanha;

  @Indexed
  @DBRef(lazy = false)
  @NotNull(message = "Informe o produto da campanha")
  private Produto produto;

  @Field("status_item")
  private StatusItem statusItem;

  @Field("qtde_minima")
  private double qtdeMinima;
  @Field("qtde_maxima")
  private double qtdeMaxima;

  @Field("valor_minimo")
  private double valorMinimo;
  @Field("valor_maximo")
  private double valorMaximo;
  @Field("valor_realizado")
  private double valorRealizado;

  public CampanhaItens() {

  }

  public CampanhaItens(CampanhaItens campanhaItens) {
    id = campanhaItens.getId();
    campanha = campanhaItens.getCampanha();
    produto = campanhaItens.getProduto();
    statusItem = campanhaItens.getStatusItem();
  }

  public Campanha getCampanha() {
    return campanha;
  }

  public void setCampanha(Campanha campanha) {
    this.campanha = campanha;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public StatusItem getStatusItem() {
    return statusItem;
  }

  public void setStatusItem(StatusItem statusItem) {
    this.statusItem = statusItem;
  }

  public double getQtdeMinima() {
    return qtdeMinima;
  }

  public void setQtdeMinima(double qtdeMinima) {
    this.qtdeMinima = qtdeMinima;
  }

  public double getQtdeMaxima() {
    return qtdeMaxima;
  }

  public void setQtdeMaxima(double qtdeMaxima) {
    this.qtdeMaxima = qtdeMaxima;
  }

  public double getValorMinimo() {
    return valorMinimo;
  }

  public void setValorMinimo(double valorMinimo) {
    this.valorMinimo = valorMinimo;
  }

  public double getValorMaximo() {
    return valorMaximo;
  }

  public void setValorMaximo(double valorMaximo) {
    this.valorMaximo = valorMaximo;
  }

  public double getValorRealizado() {
    return valorRealizado;
  }

  public void setValorRealizado(double valorRealizado) {
    this.valorRealizado = valorRealizado;
  }

}
