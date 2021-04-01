package com.ihc.apirest.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(schema = "dbo", name = "clientes_registro")
public class Cliente implements UserDetails
{
  private static final long serialVersionUID = 1L;
  @Id
	private String cedula;
	private String nombres;
	@Column(name = "codprovincia")
	private String codProvincia;
	@Column(name = "codciudad")
	private String codCiudad;
	private String direccion;
	private String correo;
	private String telefono;
	@Column(name = "clave", updatable = false)
	private String clave;
	private Date fecha;
	private String direccionEntrega;
	private String latitud;
	private String longitud;
	private int estado;

	@Transient
	private String claveIngresada;
	@Transient
	private String nuevaClave;
	@Transient
	private String nuevoCorreo;
	@Transient
	private String token;

	// Pendiente por definir en tiny
	// private String sexo;
	// private String pais;
	// private String politicas;




	public Cliente(String cedula) 
	{
		this.cedula = cedula;
	}




	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<GrantedAuthority> lstRoles = new ArrayList<>();
    lstRoles.add(new SimpleGrantedAuthority(Rol.ROLE_ACUATEX_CLIENTE.name()));
    
		return lstRoles;
	}

	@JsonIgnore
	@Override
	public String getPassword() 
	{
		return clave;
	}

	@JsonIgnore
	@Override
	public String getUsername() 
	{
		return correo;
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