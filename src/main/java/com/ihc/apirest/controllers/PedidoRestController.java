package com.ihc.apirest.controllers;

// import java.util.Formatter;
import java.util.List;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.models.Pedido;
import com.ihc.apirest.models.ProductoPedido;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
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

  static final Long ID_ESTADO_PENDIENTE = (long) 100;
  static final Long ID_ESTADO_ACEPTADO = (long) 101;



  /**
   * Método que permite crear un nuevo pedido
   * @param pedido a crear
   * @return True si el pedido fue creado, en caso contrario False
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
  public ResponseEntity<Boolean> actualizarPedido(@RequestBody Pedido pedido)
  {
    boolean isActualizado = false;
    try 
    {
      //Validamos que el pedido siga pendiente y que ninguna tienda lo haya tomado antes
      Long idPedidoPendiente = pedidoService.getPedidosByIdPedidoAndIdEstado(pedido.getIdPedido(), ID_ESTADO_PENDIENTE);

      if(null != idPedidoPendiente)
      {
        //Actualizando el estado del pedido con la tienda que lo tomó
        pedidoService.actualizarPedido(pedido.getIdTienda(), ID_ESTADO_ACEPTADO, pedido.getIdPedido());
        isActualizado = true;
      }
      
      return new ResponseEntity<Boolean>(isActualizado, HttpStatus.CREATED);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(isActualizado, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar el estado de un pedido
   * @param pedido actualizar
   * @return True si el pedido fue actualizado, en caso contrario False
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/pedidos/estado")
  public ResponseEntity<Boolean> actualizarEstadoPedido(@RequestBody Pedido pedido)
  {
    try 
    {
      //Actualizando el estado del pedido
      pedidoService.actualizarEstadoPedido(pedido);
      
      return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
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
  @GetMapping(value = "/pedidos")
  public ResponseEntity<List<Pedido>> getPedidosPendientes() 
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getPedidosByIdEstado(ID_ESTADO_PENDIENTE);

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener todos los pedidos aceptados por la tienda
   * @return Listado de pedidos
   */
  @GetMapping(value = "/pedidos/tienda/{idTienda}")
  public ResponseEntity<List<Pedido>> getHistorialPedidosTienda(@PathVariable("idTienda") Long idTienda)
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getHistorialPedidosTienda(idTienda, ID_ESTADO_ACEPTADO);

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
  @GetMapping(value = "/pedidos/cliente/{idCliente}")
  public ResponseEntity<List<Pedido>> getHistorialPedidosCliente(@PathVariable("idCliente") Long idCliente)
  {
    try
    {
      List<Pedido> lstPedidos = pedidoService.getHistorialPedidosCliente(new Cliente(idCliente));

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }




  // /**
  //  * Método que permite obtener todos los pedidos según el cliente
  //  * @param headerAuthorization contiene el token
  //  * @return Listado de pedidos del cliente
  //  */
  // // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  // @GetMapping(value = "/pedidos")
  // public ResponseEntity<List<Pedido>> getAllPedidosByCliente(@RequestHeader("Authorization") String headerAuthorization) 
  // {
  //   try
  //   {
  //     String token = jwtService.getToken(headerAuthorization);
  //     String correo = jwtService.getUserNameFromToken(token);

  //     List<Pedido> lstPedidos = pedidoService.getAllPedidosByCorreo(correo);

  //     return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
  //   }
  //   catch (Exception e) 
  //   {
  //     return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
  //   }
  // }
}