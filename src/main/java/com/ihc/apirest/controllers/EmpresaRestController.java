package com.ihc.apirest.controllers;

import com.ihc.apirest.models.Empresa;
import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.service.EmpresaService;
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
public class EmpresaRestController 
{
  @Autowired
  EmpresaService empresaService;

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  JwtService jwtService;


  /**
   * Método que permite regisgrar una empresa en BD
   * @param empresa a registrar
   * @return true si la empresa fue registrada, en caso contrario false
   */
  // @PreAuthorize("hasRole('ACUATEX_CLIENTE')")
  @PostMapping("/empresas")
  public ResponseEntity<Boolean> registrarEmpresa(@RequestBody Empresa empresa)
  {
    try 
    {
      empresaService.registrarEmpresa(empresa);

      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar unan empresa en BD
   * @param empresa actualizar
   * @return true si la empresa fue actualizada, en caso contrario false
   */
  // @PreAuthorize("hasRole('ACUATEX_CLIENTE')")
  @PutMapping("/empresas")
  public ResponseEntity<Boolean> actualizarEmpresa(@RequestBody Empresa empresa)
  {
    try 
    {
      empresaService.actualizarEmpresa(empresa);

      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener la empresa a partir del token del usuario
   * @param token que contiene el username
   * @return Empresa encontrada
   */
  @GetMapping(value = "/empresas")
  public ResponseEntity<Empresa> getEmpresa(@RequestHeader("Authorization") String headerAuthorization) 
  {
    try
    {
      String token = jwtService.getToken(headerAuthorization);
      String userName = jwtService.getUserNameFromToken(token);

      Usuario usuario = usuarioService.getUsuarioByUserName(userName);

      Empresa empresa = empresaService.getEmpresaById(usuario.getIdEntidad());
      
      return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Empresa>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}