package com.ihc.apirest.controllers;

import java.util.List;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.models.Pedido;
import com.ihc.apirest.models.ProductoPedido;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.PedidoService;
import com.ihc.apirest.utilidades.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class PedidoRestController 
{
  @Autowired
  PedidoService pedidoService;

  @Autowired
  JwtService jwtService;




  /**
   * Método que permite crear un nuevo pedido
   * @param pedido a crear
   * @return true si el pedido fue creado, en caso contrario false
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PostMapping(value="/pedidos")
  public ResponseEntity<String> registrarPedido(@RequestBody Pedido pedido)
  {
    try 
    {
      //Se hace un set de pedido en todos los productos, ya que javascript no adminte estructuras ciclicas en el caso de [ProductoPedido] que contiene a [Pedido] 
      //y este a su vez contiene a [ProductoPedido], lo cual imposibilita enviar un entity de [Pedido] desde la App
      for (ProductoPedido productoPedido : pedido.getLstProductosPedido()) 
      {
          productoPedido.setPedido(pedido);    
      }

      Pedido pedidoBD = pedidoService.registrarPedido(pedido);

      return new ResponseEntity<String>(pedidoBD.getIdPedido().toString(), HttpStatus.CREATED);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar una pedido
   * @param pedido a actualizar
   * @return true si el pedido fue actualizado, en caso contrario false
   */
  @PutMapping("/pedidos")
  public ResponseEntity<Boolean> aceptarPedido(@RequestBody Pedido pedido)
  {
    try 
    {
      //Validamos que el pedido siga pendiente y que ninguna sucursal lo haya tomado antes
      Long idPedidoPendiente = pedidoService.getPedidoByIdPedidoAndIdEstado(pedido.getIdPedido(), Constantes.ESTADO_PENDIENTE);

      if(null != idPedidoPendiente)
      {
        //Actualizando el estado del pedido con la sucursal que lo tomó
        pedidoService.aceptarPedido(pedido.getIdSucursal(), Constantes.ESTADO_ACEPTADO, pedido.getIdPedido());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
      }
      
      return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar el estado de un pedido
   * @param idPedido Id del pedido
   * @param idEstado Id del estado
   * @return true si el pedido fue actualizado, en caso contrario false
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/pedidos/{idPedido}/estados/{idEstado}")
  public ResponseEntity<Boolean> actualizarEstadoPedido(@PathVariable("idPedido") Long idPedido, @PathVariable("idEstado") Long idEstado)
  {
    try 
    {
      //Actualizando el estado del pedido
      pedidoService.actualizarEstadoPedido(idEstado, idPedido);
      
      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener todos los pedidos PENDIENTES
   * @return Listado de pedidos
   */
  @GetMapping(value = "/pedidos/pendientes")
  public ResponseEntity<List<Pedido>> getPedidosPendientes() 
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getPedidosByIdEstado(Constantes.ESTADO_PENDIENTE);

      if(lstPedidos.isEmpty())
      {
        return new ResponseEntity<List<Pedido>>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener todos los pedidos aceptados por la sucursal
   * @return Listado de pedidos
   */
  @GetMapping(value = "/pedidos/sucursales/{idSucursal}")
  public ResponseEntity<List<Pedido>> getHistorialPedidosSucursal(@PathVariable("idSucursal") Long idSucursal)
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getHistorialPedidosSucursal(idSucursal, Constantes.ESTADO_ACEPTADO);

      if(lstPedidos.isEmpty())
      {
        return new ResponseEntity<List<Pedido>>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener todos los pedidos realizados por el cliente
   * @return Listado de pedidos
   */
  @GetMapping(value = "/pedidos/clientes/{idCliente}")
  public ResponseEntity<List<Pedido>> getHistorialPedidosCliente(@PathVariable("idCliente") Long idCliente)
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getHistorialPedidosCliente(new Cliente(idCliente));

      if(lstPedidos.isEmpty())
      {
        return new ResponseEntity<List<Pedido>>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}