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
   * Método que permite obtener un cliente según su correo
   * @param correo con el cual se buscara el cliente en BD
   * @return Cliente encontrado
   */
  public Cliente getClienteByCorreo(String correo) 
  {
    return clienteRepository.findByCorreo(correo);
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
   * Método que permite registrar un cliente en BD
   * @param cliente a registrar
   * @return Cliente registrado
   */
  public Integer actualizarDatosAccesoCliente(Cliente cliente) 
  {
    return clienteRepository.actualizarDatosAccesoCliente(cliente.getCorreo(), cliente.getClave(), cliente.getCedula());
  }


  /**
   * Método que permite restaurar temporalmente la clave de un cliente en BD
   * @param cliente a restaurar clave
   * @return Cliente registrado
   */
  public Integer restaurarClave(String clave, String correo)
  {
    return clienteRepository.restaurarClave(clave, correo);
  }


  /**
   * Método que permite validar si un correo existe en bd
   * @param correo a valdiar
   * @return true si existe el correo, en caso contrario false
   */
  public boolean existeClienteByCorreo(String correo) 
  {
    return clienteRepository.existsByCorreo(correo);
  }
}
