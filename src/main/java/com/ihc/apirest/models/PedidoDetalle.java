package com.ihc.apirest.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@IdClass(PedidoDetallePK.class)
@Table(schema="dbo", name = "pedido_detalle")
public class PedidoDetalle
{
  @Id
  private Integer secuencia;
  private String codArticulo;
  private String codPromo;
  private String umedida;
  private Double cantidad;
  private Double bonificacion;
  private Double precioVenta;
  private Double porcDescuento;
  private Double ice;
  private Double iva;
  private String detalle;

  @Transient
  private String talla;

  @Id
  @JoinColumn(name = "nro_pedido")
	@ManyToOne(optional = false)
	@JsonIgnore
  private Pedido pedido;


  public String getCodArticulo() 
  {
    return codArticulo.substring(0,(codArticulo.indexOf("T") + 1));
  }

  public String getTalla() 
  {
    return codArticulo.substring(codArticulo.indexOf("T") + 1);
  }
}
