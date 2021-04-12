package com.ihc.apirest.service;

import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UsuarioService 
{
  @Autowired
  private UsuarioRepository usuarioRepository;


  /**
   * Método que permite obtener un usuario según su userName
   * @param userName con el cual se buscara el usuario en BD
   * @return Usuario encontrado
   */
  public Usuario getUsuarioByUserName(String userName) 
  {
    return usuarioRepository.findByUserName(userName);
  }


  /**
   * Método que permite registrar un usuario en BD
   * @param usuario a registrar
   * @return Usuario registrado
   */
  public Usuario registrarUsuario(Usuario usuario) 
  {
    return usuarioRepository.save(usuario);
  }


  /**
   * Método que permite actualizar el password del usuario en BD
   * @param usuario que contiene el password a actualizar
   * @return 1 si el password fue actualizado, en caso contrario 0
   */
  public Integer actualizarPasswordUsuario(Usuario usuario) 
  {
    return usuarioRepository.actualizarPasswordUsuario(usuario.getPassword(), usuario.getUsername());
  }


  /**
   * Método que permite actualizar el userName del usuario en BD
   * @param usuario que contiene el userName a actualizar
   * @return 1 si el userName fue actualizado, en caso contrario 0
   */
  public Integer actualizarUserNameUsuario(Usuario usuario) 
  {
    return usuarioRepository.actualizarUserNameUsuario(usuario.getNuevoUserName(), usuario.getUsername());
  }


  /**
   * Método que permite restaurar temporalmente la password de un usuario en BD
   * @param usuario a restaurar password
   * @return Usuario registrado
   */
  public Integer restaurarPassword(String password, String userName)
  {
    return usuarioRepository.restaurarPassword(password, userName);
  }


  /**
   * Método que permite validar si un userName existe en bd
   * @param userName a valdiar
   * @return true si existe el userName, en caso contrario false
   */
  public boolean existeUsuarioByUserName(String userName) 
  {
    return usuarioRepository.existsByUserName(userName);
  }
}
