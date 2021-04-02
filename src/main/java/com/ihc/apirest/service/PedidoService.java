package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Cliente;
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
   * Método que permite registrar un pedido nuevo en BD
   * @param pedido a registar
   * @return Pedido creado con su nuevo ID
   */
  public Pedido registrarPedido(Pedido pedido)
  {
    return pedidoRepository.save(pedido);
  }


  /**
   * Método que permite actualizar una pedido
   * @param idTienda Id de la tienda que tomo el pedido
   * @param idEstado Id del estado Aceptado
   * @param idPedido Id del pedido
   */
  public void actualizarPedido(Long idTienda, Long idEstado, Long idPedido)
  {
    pedidoRepository.actualizarPedido(idTienda, idEstado, idPedido);
  }



  /**
   * Método que permite actualizar el estado de un pedido
   * @param pedido que contiene el estado
   */
  public void actualizarEstadoPedido(Pedido pedido)
  {
    pedidoRepository.actualizarEstadoPedido(pedido.getIdEstado(), pedido.getIdPedido());
  }



  /**
   * Método que permite obtener un pedido según su estado
   * @param idEstado Id estado del pedido
   * @param idPedido Id del pedido
   * @return Pedido
   */
  public Long getPedidosByIdPedidoAndIdEstado(Long idPedido, Long idEstado)
  {
    return pedidoRepository.findByIdPedidoAndIdEstado(idPedido, idEstado);
  }



  /**
   * Método que permite obtener todos los pedidos según su estado
   * @param idEstado Id estado
   * @return Listado de pedidos
   */
  public List<Pedido> getPedidosByIdEstado(Long idEstado)
  {
    return pedidoRepository.findByIdEstado(idEstado);
  }



  /**
   * Método que permite obtener todos los pedidos de una tienda con estado "ACEPTADO"
   * @param idTienda Id del tienda
   * @param idEstado Id del estado
   * @return Listado de pedidos aceptados
   */
  public List<Pedido> getHistorialPedidosTienda(Long idTienda, Long idEstado)
  {
    return pedidoRepository.findByIdTiendaAndIdEstado(idTienda, idEstado);
  }



  /**
   * Método que permite obtener todos los pedidos del cliente del día actual
   * @param idCliente Id del cliente
   * @param idEstado Id del estado
   * @return Listado de pedidos aceptados
   */
  public List<Pedido> getHistorialPedidosCliente(Cliente cliente)
  {
    return pedidoRepository.findByCliente(cliente);
  }
}
