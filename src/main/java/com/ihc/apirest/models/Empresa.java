package com.ihc.apirest.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(schema="domicilios")
public class Empresa
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmpresa;
	private Long idGeografia;
	private Integer idEstado;
	private String nit;
	private String nombre;
	private String telefono;
	private String direccion;
	private String email;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private List<Sucursal> lstSucursales;
}