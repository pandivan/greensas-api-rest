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
   * Método que permite registrar un pedido nuevo
   * @param pedido a registar
   * @return Pedido creado con su nuevo ID
   */
  public Pedido registrarPedido(Pedido pedido)
  {
    return pedidoRepository.save(pedido);
  }


  /**
   * Método que permite actualizar una pedido
   * @param idSucursal Id de la sucursal que tomo el pedido
   * @param idEstado Id del estado Aceptado
   * @param idPedido Id del pedido
   */
  public void aceptarPedido(Long idSucursal, Long idEstado, Long idPedido)
  {
    pedidoRepository.aceptarPedido(idSucursal, idEstado, idPedido);
  }



  /**
   * Método que permite actualizar el estado de un pedido
   * @param idEstado Id el estado
   * @param idPedido Id del pedido
   */
  public void actualizarEstadoPedido(Long idEstado, Long idPedido)
  {
    pedidoRepository.actualizarEstadoPedido(idEstado, idPedido);
  }



  /**
   * Método que permite obtener un pedido según su estado
   * @param idEstado Id estado del pedido
   * @param idPedido Id del pedido
   * @return Pedido
   */
  public Long getPedidoByIdPedidoAndIdEstado(Long idPedido, Long idEstado)
  {
    return pedidoRepository.findByIdPedidoAndIdEstado(idPedido, idEstado);
  }



  /**
   * Método que permite obtener todos los pedidos
   * @param idEstado Id estado
   * @return Listado de pedidos
   */
  public List<Pedido> getAllPedidos()
  {
    return pedidoRepository.findAll();
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
   * Método que permite obtener todos los pedidos de una sucursal con estado "ACEPTADO"
   * @param idSucursal Id de la sucursal
   * @param idEstado Id del estado
   * @return Listado de pedidos aceptados
   */
  public List<Pedido> getPedidosSucursal(Long idSucursal, Long idEstado)
  {
    return pedidoRepository.findByIdSucursalAndIdEstado(idSucursal, idEstado);
  }



  /**
   * Método que permite obtener todos los pedidos del cliente del día actual
   * @param idCliente Id del cliente
   * @param idEstado Id del estado
   * @return Listado de pedidos aceptados
   */
  public List<Pedido> getPedidosCliente(Cliente cliente)
  {
    return pedidoRepository.findByCliente(cliente);
  }
}
