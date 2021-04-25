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
   * Método que permite obtener un cliente según su Id
   * @param idCliente con el cual se buscara el cliente
   * @return Cliente encontrado
   */
  public Cliente getClienteById(Long idCliente) 
  {
    return clienteRepository.findByIdCliente(idCliente);
  }



  /**
   * Método que permite registrar un cliente
   * @param cliente a registrar
   * @return Cliente registrado
   */
  public Cliente registrarCliente(Cliente cliente) 
  {
    return clienteRepository.save(cliente);
  }



  /**
   * Método que permite actualizar un cliente
   * @param cliente actualizar
   * @return Cliente actualizado
   */
  public Cliente actualizarCliente(Cliente cliente) 
  {
    return clienteRepository.save(cliente);
  }
}
