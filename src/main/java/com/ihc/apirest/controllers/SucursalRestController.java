package com.ihc.apirest.controllers;

import com.ihc.apirest.models.Sucursal;
import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.service.SucursalService;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;





@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class SucursalRestController 
{
  @Autowired
  SucursalService sucursalService;

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  JwtService jwtService;


  /**
   * Método que permite regisgrar una sucursal en BD
   * @param sucursal a registrar
   * @return true si la sucursal fue registrada, en caso contrario false
   */
  // @PreAuthorize("hasRole('ACUATEX_CLIENTE')")
  @PostMapping("/sucursales")
  public ResponseEntity<Boolean> registrarSucursal(@RequestBody Sucursal sucursal)
  {
    try 
    {
      sucursalService.registrarSucursal(sucursal);

      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar unan sucursal en BD
   * @param sucursal actualizar
   * @return true si la sucursal fue actualizada, en caso contrario false
   */
  // @PreAuthorize("hasRole('ACUATEX_CLIENTE')")
  @PutMapping("/sucursales")
  public ResponseEntity<Boolean> actualizarSucursal(@RequestBody Sucursal sucursal)
  {
    try 
    {
      sucursalService.actualizarSucursal(sucursal);

      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener la sucursal a partir del token del usuario
   * @param token que contiene el username
   * @return Sucursal encontrada
   */
  @GetMapping(value = "/sucursales")
  public ResponseEntity<Sucursal> getSucursal(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String userName = jwtService.getUserNameFromToken(token);

      Usuario usuario = usuarioService.getUsuarioByUserName(userName);

      Sucursal sucursal = sucursalService.getSucursalById(usuario.getIdEntidad());
      
      return new ResponseEntity<Sucursal>(sucursal, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Sucursal>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}