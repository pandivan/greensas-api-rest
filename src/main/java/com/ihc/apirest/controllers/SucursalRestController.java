package com.ihc.apirest.controllers;

import com.ihc.apirest.models.Sucursal;
import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.SucursalService;
import com.ihc.apirest.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





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

      if(null != usuario)
      {
        Sucursal sucursal = sucursalService.getSucursalById(usuario.getIdEntidad());
        
        if(null != sucursal)
        {
          return new ResponseEntity<Sucursal>(sucursal, HttpStatus.OK);
        }
      }

      return new ResponseEntity<Sucursal>(HttpStatus.NO_CONTENT);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Sucursal>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}