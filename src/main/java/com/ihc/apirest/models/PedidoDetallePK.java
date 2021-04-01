package com.ihc.apirest.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Clase comodin que representa la primary key compuesta de la tabla [pedido_detalle]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PedidoDetallePK implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer secuencia;
  private Pedido pedido;
}
