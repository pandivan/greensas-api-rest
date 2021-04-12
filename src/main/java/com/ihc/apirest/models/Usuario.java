package com.ihc.apirest.models;

import java.util.ArrayList;
import java.util.Collection;
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
@Table(schema="domicilios")
public class Usuario implements UserDetails
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private Long idEntidad;
	private Long idEstado;
	private String userName;
	@Column(name = "password", updatable = false)
	private String password;
	private String tipo;

	@Transient
	private String nuevoPassword;
	
	@Transient
	private String nuevoUserName;





	
	
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


	@Override
	public String getPassword() 
	{
		return password;
	}



	@Override
	public String getUsername() 
	{
		return userName;
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