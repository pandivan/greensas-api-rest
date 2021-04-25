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
    @Query("update Usuario c SET c.password = ?1 where c.idUsuario = ?2")
    Integer actualizarPasswordUsuario(String password, Long idUsuario);


    @Modifying
    @Query("update Usuario c SET c.userName = ?1 where c.idUsuario = ?2")
    Integer actualizarUserNameUsuario(String nuevoUserName, Long idUsuario);


    @Modifying
    @Query("update Usuario c SET c.password= ?1 where c.userName = ?2")
    Integer restaurarPassword(String password, String userName);


    Usuario findByUserName(String userName);


    Usuario findByIdUsuario(Long idUsuario);


    boolean existsByUserName(String userName);
}