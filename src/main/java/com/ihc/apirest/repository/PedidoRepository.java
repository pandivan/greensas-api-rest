package com.ihc.apirest.repository;

import java.util.List;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.models.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> 
{

  @Modifying
  @Query("update Pedido p SET p.idSucursal = ?1, p.idEstado = ?2 where p.idPedido = ?3")
  void aceptarPedido(Long idSucursal, Long idEstado, Long idPedido);



  @Modifying
  @Query("update Pedido p SET p.idEstado = ?1 where p.idPedido = ?2")
  void actualizarEstadoPedido(Long idEstado, Long idPedido);



  @Query("select p.idPedido from Pedido p where p.idPedido = ?1 and p.idEstado = ?2")
  Long findByIdPedidoAndIdEstado(Long idPedido, Long idEstado);



  List<Pedido> findByIdEstado(Long idEstado);



  List<Pedido> findByIdSucursalAndIdEstado(Long idSucursal, Long idEstado);

  

  List<Pedido> findByCliente(Cliente cliente);
}