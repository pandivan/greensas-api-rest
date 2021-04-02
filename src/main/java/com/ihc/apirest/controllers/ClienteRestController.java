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
  public ResponseEntity<Cliente> actualizarPasswordCliente(@RequestBody Cliente cliente)
  {
    try 
    {  
      String email = jwtService.getUserNameFromToken(cliente.getToken());

      Cliente clienteBD = clienteService.getClienteByEmail(email);


      if(null != clienteBD)
      {
        //Se hace set del password por temas de sql update
        cliente.setPassword(clienteBD.getPassword());
        cliente.setEmail(clienteBD.getEmail());
        cliente.setCedula(clienteBD.getCedula());

        if(null != cliente.getNuevoPassword())
        {
          cliente.setPassword(bcrypt.encode(cliente.getNuevoPassword()));
        }

        if(null != cliente.getNuevoEmail())
        {
          if(clienteService.existeClienteByEmail(cliente.getNuevoEmail()))
          {
            return new ResponseEntity<Cliente>(HttpStatus.CREATED);
          }
          cliente.setEmail(cliente.getNuevoEmail());

          //Al actualizar el email estamos cambiando el username de la aplicación, recordar que este username esta impreso en el token, es por eso que debemos genear un token nuevo
          String token = jwtService.generarToken(cliente);

          cliente.setToken(token);
        }

        clienteService.actualizarPasswordCliente(cliente);

        //Se quitan datos sensibles del usuario por seguridad
        cliente.setNuevoPassword(null);
        cliente.setPassword(null);
        cliente.setCedula(null);
        cliente.setNuevoEmail(null);

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
      String email = jwtService.getUserNameFromToken(token);

      Cliente clienteBD = clienteService.getClienteByEmail(email);

      //Se quitan datos sensibles del usuario por seguridad
      clienteBD.setPassword(null);
      
      return new ResponseEntity<Cliente>(clienteBD, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener el email del cliente a partir del token
   * @param headerAuthorization contiene el token
   * @return Email del cliente
   */
  @GetMapping(value = "/clientes/mail")
  public ResponseEntity<String> getEmailCliente(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String email = jwtService.getUserNameFromToken(token);
      
      return new ResponseEntity<String>(email, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}