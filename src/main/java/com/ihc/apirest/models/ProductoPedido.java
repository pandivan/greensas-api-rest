package com.ihc.apirest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(schema="domicilios")
public class ProductoPedido
{
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProductoPedido;
	
	@JoinColumn(name = "idPedido")
	@ManyToOne(optional = false)
	@JsonIgnore //Se ignora este atributo ya que internamente "Pedido" tiene un Listado de <ProductoPedido> y esto genera un ciclo infinito a la hora de cargar un objeto "Pedido" que no permite ser enviado como respuesta
  private Pedido pedido;
	
	@JoinColumn(name = "idProducto")
	@ManyToOne(optional = false)
	private Producto producto;

	private int cantidad;
	private Double valor;
}