package com.ihc.apirest.repository;

import java.util.List;

import com.ihc.apirest.models.Ciudad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> 
{
	
	@Query(value = "SELECT (c.codigo_provincia+c.codigo_ciudad) as id, c.codigo_pais, c.codigo_provincia, c.codigo_ciudad, c.nombre from ciudades c order by c.codigo_provincia, c.codigo_ciudad, c.nombre", nativeQuery = true)
	List<Ciudad> getAllCiudades();

}