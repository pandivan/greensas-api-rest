package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Pedido;
import com.ihc.apirest.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PedidoService 
{
  @Autowired
  PedidoRepository pedidoRepository;

  
  /**
   * Método que permite obtener la secuencia de la tabla pedido
   * @return Id de la secuencia
   */
  public String maxNroPedido()
  {
    return pedidoRepository.maxNroPedido();
  }
  


  /**
   * Método que permite registrar un pedido nuevo en BD
   * @param pedido a registar
   * @return Pedido creado con su nuevo ID
   */
  public Pedido registrarPedido(Pedido pedido)
  {
    return pedidoRepository.save(pedido);
  }


  /**
   * Método que permite actualizar el estado de un pedido
   * @param pedido que contiene el estado
   */
  public void actualizarEstadoPedido(Pedido pedido)
  {
    pedidoRepository.actualizarEstadoPedido(pedido.getEstado(), pedido.getNroPedido(), pedido.getCliente());
  }


  /**
   * Método que permite obtener todos los pedidos según el cliente
   * @param correo del cliente
   * @return Listado de pedidos del cliente
   */
  public List<Pedido> getAllPedidosByCorreo(String correo)
  {
    return pedidoRepository.findByCorreo(correo);
  }
}
