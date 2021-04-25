package com.ihc.apirest.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class Cliente
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	private Long idBarrio;
	private Long idEstado;
	private String cedula;
	private String nombre;
	private String telefono;
	private String direccion1;
	private String direccion2;
	private String direccion3;
	private String email;
	private Date fechaNacimiento;
	private String sexo;
	
	@Transient
	private String password;


	public Cliente(Long idCliente) 
	{
		this.idCliente = idCliente;
	}
}