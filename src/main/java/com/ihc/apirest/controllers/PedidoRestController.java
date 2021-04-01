package com.ihc.apirest.controllers;

import java.util.Formatter;
import java.util.List;

import com.ihc.apirest.models.Pedido;
import com.ihc.apirest.models.PedidoDetalle;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

  static final Integer ID_ESTADO_PENDIENTE = 100;
  static final Integer ID_ESTADO_ACEPTADO = 101;



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
      //Obteniendo el numero maximo del pedido para genear a partir de este el nuevo ID del pedido
      String nroPedidoBD = pedidoService.maxNroPedido();

      if(null == nroPedidoBD)
      {
        /**
         * 000000   = No se que es jaja
         * 98       = Es el codigo de usuario web
         * 0000001  = Es el id incremental del pedido
         */
        
        nroPedidoBD = "000000980000001";
      }
      else
      {
        Formatter formatter = new Formatter();
        nroPedidoBD = "00000098".concat(formatter.format("%07d", Long.parseLong(nroPedidoBD.substring(8)) + 1).toString());
        formatter.close();
      }
      
      pedido.setNroPedido(nroPedidoBD);


      //Se hace un set de pedido en todos los pedidos detalles, ya que javascript no adminte estructuras ciclicas en el caso de [PedidoDetalle] que contiene a [Pedido] 
      //y este a su vez contiene a [PedidoDetalle], lo cual imposibilita enviar un entity de [Pedido] desde la Web
      for (PedidoDetalle pedidoDetalle : pedido.getLstPedidoDetalle()) 
      {
        pedidoDetalle.setPedido(pedido);
      }

      Pedido pedidoBD = pedidoService.registrarPedido(pedido);

      return new ResponseEntity<String>(pedidoBD.getNroPedido(), HttpStatus.CREATED);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar el estado de un pedido
   * @param pedido actualizar
   * @return True si el pedido fue actualizado, en caso contrario False
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/pedidos")
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
   * Método que permite obtener todos los pedidos según el cliente
   * @param headerAuthorization contiene el token
   * @return Listado de pedidos del cliente
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @GetMapping(value = "/pedidos")
  public ResponseEntity<List<Pedido>> getAllPedidosByCliente(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String correo = jwtService.getUserNameFromToken(token);

      List<Pedido> lstPedidos = pedidoService.getAllPedidosByCorreo(correo);

      return new ResponseEntity<List<Pedido>>(lstPedidos, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Pedido>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}