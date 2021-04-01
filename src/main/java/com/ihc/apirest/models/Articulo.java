package com.ihc.apirest.models;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Articulo
{
  @Id
	private String codigo;
	private String codigoArticulo;
	private String grupo;
	@Transient
	private String nombre;
	@Transient
	private String descripcion;
	private int cantidad;
	private Double precio;
	private String talla;
	private String detalle;
	@Transient
	private List<String> lstArticulosLook;
	@Transient
	private Set<String> lstTallas;


	
	public String getNombre() 
  {
    return getCampo(0);
  }


	public String getDescripcion() 
  {
    return getCampo(1);
  }


	public List<String> getLstArticulosLook() 
  {
		lstArticulosLook = Arrays.asList(detalle.split("-"));

    return lstArticulosLook.subList(2, lstArticulosLook.size());
  }
	



	/**
	 * Método que permite obtener del campo detalle el nombre y la descripción del articulo
	 * @param index posición del campo solicitado nombre[0] y descripción[1]
	 * @return campo solicitado
	 */
	private String getCampo(int index)
	{
		String campo = null;

		if(null != detalle)
		{
			String[] lstDetalles = detalle.split("-");

			if(0 != lstDetalles.length)
			{
				campo = lstDetalles[index];
			}
		}

		return campo;
	}
}