package com.ihc.apirest.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ihc.apirest.security.Rol;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema="dimension")
public class Cliente implements UserDetails
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	private Long idBarrio;
	private String cedula;
	private String nombre;
	private String telefono;
	private String direccion;
	private String email;
	private Date fechaNacimiento;
	private String sexo;
	private String tipoCliente;
	@Column(name = "clave", updatable = false)
	private String password;
	private String barrio;

	@Transient
	private boolean tendero;

	@Transient
	private String nuevoPassword;
	
	@Transient
	private String nuevoEmail;
	
	@Transient
	private String token;


	

	public Cliente(Long idCliente) 
	{
		this.idCliente = idCliente;
	}



	public Cliente(Long idCliente, String nombre, boolean tendero) 
	{
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.tendero = tendero;
	}




	@JsonIgnore
	@Override
	public String getPassword() 
	{
		return password;
	}


	
	/**************************************************************************************************************************
	 * Metodos derivados de la implementación "UserDetails" para el manejo de seguridad y autenticación del usuario
	 * ************************************************************************************************************************/

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> lstRoles = new ArrayList<>();
    lstRoles.add(new SimpleGrantedAuthority(Rol.ROLE_CLIENTE.name()));
    
		return lstRoles;
	}


	@JsonIgnore
	@Override
	public String getUsername() 
	{
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() 
	{
		return true;
	}
}