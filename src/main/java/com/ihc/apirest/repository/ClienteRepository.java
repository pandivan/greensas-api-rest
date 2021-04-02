package com.ihc.apirest.repository;

import com.ihc.apirest.models.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>
{
    
    @Modifying
    @Query("update Cliente c SET c.email = ?1, c.password= ?2 where c.cedula = ?3")
    Integer actualizarPasswordCliente(String email, String password, String cedula);


    @Modifying
    @Query("update Cliente c SET c.password= ?1 where c.email = ?2")
    Integer restaurarPassword(String password, String email);


    boolean existsByCedulaAndPassword(String cedula, String password);


    boolean existsByEmail(String email);


    Cliente findByEmailAndPassword(String email, String password);


    Cliente findByEmail(String email);
}