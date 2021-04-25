package com.ihc.apirest.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema = "domicilios")
public class Pedido 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;
	private Long idSucursal;
	@JoinColumn(name = "idCliente")
	@ManyToOne(optional = false)
	private Cliente cliente;
	private Long idEstado;
	@Column(name = "fecha_pedido", insertable = false)
	private Date fechaPedido;
	

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ProductoPedido> lstProductosPedido;
}
