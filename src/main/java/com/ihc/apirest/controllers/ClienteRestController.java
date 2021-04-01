package com.ihc.apirest.controllers;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.service.ClienteService;
import com.ihc.apirest.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;





@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class ClienteRestController 
{
  @Autowired
  ClienteService clienteService;

  @Autowired
  JwtService jwtService;

  @Autowired
  BCryptPasswordEncoder bcrypt;

  


  /**
   * Método que permite actualizar los datos de acceso de un cliente en BD
   * @param cliente, Cliente actualizar
   * @return Cliente actualizado
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/clientes/datos_acceso")
  public ResponseEntity<Cliente> actualizarDatosAccesoCliente(@RequestBody Cliente cliente)
  {
    try 
    {  
      String correo = jwtService.getUserNameFromToken(cliente.getToken());

      Cliente clienteBD = clienteService.getClienteByCorreo(correo);


      if(null != clienteBD && bcrypt.matches(cliente.getClaveIngresada(), clienteBD.getClave()))
      {
        //Se hace set de la clave por temas de sql update
        cliente.setClave(clienteBD.getClave());
        cliente.setCorreo(clienteBD.getCorreo());
        cliente.setCedula(clienteBD.getCedula());

        if(null != cliente.getNuevaClave())
        {
          cliente.setClave(bcrypt.encode(cliente.getNuevaClave()));
        }

        if(null != cliente.getNuevoCorreo())
        {
          if(clienteService.existeClienteByCorreo(cliente.getNuevoCorreo()))
          {
            return new ResponseEntity<Cliente>(HttpStatus.CREATED);
          }
          cliente.setCorreo(cliente.getNuevoCorreo());

          //Al actualizar el correo estamos cambiando el user name de la aplicación, recordar que este user name esta impreso en el token, es por eso que debemos genear un token nuevo
          String token = jwtService.generarToken(cliente);

          cliente.setToken(token);
        }

        clienteService.actualizarDatosAccesoCliente(cliente);

        //Se quitan datos sensibles del usuario por seguridad
        cliente.setClaveIngresada(null);
        cliente.setNuevaClave(null);
        cliente.setClave(null);
        cliente.setCedula(null);
        cliente.setNuevoCorreo(null);

        //Se retorna el cliente con el nuevo token
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
      }

      return new ResponseEntity<Cliente>(HttpStatus.NO_CONTENT);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar todos los datos de un cliente en BD
   * @param cliente, Cliente actualizar
   * @return True si el cliente fue actualizado, en caso contrario false
   */
  // @PreAuthorize("hasRole('ACUATEX_CLIENTE')")
  @PutMapping("/clientes")
  public ResponseEntity<Boolean> actualizarCliente(@RequestBody Cliente cliente)
  {
    try 
    {
      clienteService.registrarCliente(cliente);

      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener el cliente a partir del token
   * @param token que contiene el user name
   * @return Cliente encontrado
   */
  @GetMapping(value = "/clientes")
  public ResponseEntity<Cliente> getCliente(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String correo = jwtService.getUserNameFromToken(token);

      Cliente clienteBD = clienteService.getClienteByCorreo(correo);

      clienteBD.setClave(null);
      
      return new ResponseEntity<Cliente>(clienteBD, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener el correo del cliente a partir del token
   * @param headerAuthorization contiene el token
   * @return Correo del cliente
   */
  @GetMapping(value = "/clientes/mail")
  public ResponseEntity<String> getCorreoCliente(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String correo = jwtService.getUserNameFromToken(token);
      
      return new ResponseEntity<String>(correo, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}