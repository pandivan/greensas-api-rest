package com.ihc.apirest.repository;

import com.ihc.apirest.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>
{
    
    @Modifying
    @Query("update Usuario c SET c.password = ?1 where c.userName = ?2")
    Integer actualizarPasswordUsuario(String password, String userName);


    @Modifying
    @Query("update Usuario c SET c.userName = ?1 where c.userName = ?2")
    Integer actualizarUserNameUsuario(String nuevoUserName, String userName);


    @Modifying
    @Query("update Usuario c SET c.password= ?1 where c.userName = ?2")
    Integer restaurarPassword(String password, String userName);


    //boolean existsByCedulaAndPassword(String cedula, String password);


    boolean existsByUserName(String userName);


    Usuario findByUserNameAndPassword(String userName, String password);


    Usuario findByUserName(String userName);
}