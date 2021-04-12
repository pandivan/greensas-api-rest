package com.ihc.apirest.service;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class ClienteService 
{
  @Autowired
  private ClienteRepository clienteRepository;


  
  /**
   * Método que permite obtener un cliente según su email
   * @param email con el cual se buscara el cliente en BD
   * @return Cliente encontrado
   */
  public Cliente getClienteByEmail(String email) 
  {
    return clienteRepository.findByEmail(email);
  }



  /**
   * Método que permite registrar un cliente en BD
   * @param cliente a registrar
   * @return Cliente registrado
   */
  public Cliente registrarCliente(Cliente cliente) 
  {
    return clienteRepository.save(cliente);
  }



  /**
   * Método que permite restaurar temporalmente la password de un cliente en BD
   * @param cliente a restaurar password
   * @return Cliente registrado
   */
  public Integer restaurarPassword(String password, String email)
  {
    return clienteRepository.restaurarPassword(password, email);
  }



  /**
   * Método que permite validar si un email existe en bd
   * @param email a valdiar
   * @return true si existe el email, en caso contrario false
   */
  public boolean existeClienteByEmail(String email) 
  {
    return clienteRepository.existsByEmail(email);
  }
}
