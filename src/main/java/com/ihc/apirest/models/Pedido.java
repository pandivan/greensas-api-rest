package com.ihc.apirest.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(schema="dbo", name = "pedido_encabezado")
public class Pedido
{
  @Id
	private String nroPedido;
  private Date fecha;
  private String hora;
  @Column(name="fechaenvio")
  private Date fechaEnvio;
  @Column(name="horaenvio")
  private String horaEnvio;
  private Integer cliente;
  private Integer sucursal;
  private String ruc;
  private String nombre;
  private String direccion;
  private String correo;
  private String telefono;
  @Column(name="codprovincia")
  private String codProvincia;
  @Column(name="codciudad")
  private String codCiudad;
  private String direccionEntrega;
  private String telefonoEntrega;
  @Column(name="codprovincia_entrega")
  private String codProvinciaEntrega;
  @Column(name="codciudad_entrega")
  private String codCiudadEntrega;
  private Integer diasPlazo;
  private String detalle;
  private Integer estado;
  private String vendedor;
  @Column(name="fechadespacho")
  private Date fechaDespacho;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  private List<PedidoDetalle> lstPedidoDetalle;
}
