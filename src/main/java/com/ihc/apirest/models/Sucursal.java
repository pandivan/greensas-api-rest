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
public class Sucursal
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSucursal;

	/**
	 * Se ignora este atributo ya que internamente "Empresa" tiene un Listado de
	 * <Sucursales> y esto genera un ciclo infinito en javascript a la hora de
	 * cargar un objeto "Empresa" que no permite ser enviado como respuesta como
	 * consecuancia solo se podra crear y actualizar una sucursal por medio del
	 * objeto "Empresa", de lo contrario debemos hacer las inserciones de empresa y
	 * sucursales por separado
	 */
	@JoinColumn(name = "idEmpresa")
	@ManyToOne(optional = false)
	@JsonIgnore
  private Empresa empresa;

	private Long idGeografia;
	private Long idEstado;
	private String nombre;
	private String telefono;
	private String direccion;
	private String horarioApertura;
	private String horarioCierre;
}