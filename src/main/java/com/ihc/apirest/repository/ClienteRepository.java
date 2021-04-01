package com.ihc.apirest.repository;

import com.ihc.apirest.models.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>
{
    //Métodos propios de spring para ejecutar consultas por medio de JPA

    @Modifying
    @Query("update Cliente c SET c.correo = ?1, c.clave= ?2 where c.cedula = ?3")
    Integer actualizarDatosAccesoCliente(String correo, String clave, String cedula);


    @Modifying
    @Query("update Cliente c SET c.clave= ?1 where c.correo = ?2")
    Integer restaurarClave(String clave, String correo);


    boolean existsByCedulaAndClave(String cedula, String clave);


    boolean existsByCorreo(String correo);


    Cliente findByCorreoAndClave(String correo, String clave);


    Cliente findByCorreo(String correo);
}