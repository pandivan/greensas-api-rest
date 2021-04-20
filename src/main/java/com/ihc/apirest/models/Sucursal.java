package com.ihc.apirest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(schema="domicilios")
public class Sucursal
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSucursal;
	private Long idEmpresa;
	private Long idGeografia;
	private Long idEstado;
	@Column(name = "id_tiempo_fecha_creacion", updatable = false)
	private Integer idTiempoFechaCreacion;
	private String nombre;
	private String telefono;
	private String direccion;
	private String horarioApertura;
	private String horarioCierre;
}