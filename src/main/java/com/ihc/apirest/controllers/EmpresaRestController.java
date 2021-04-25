package com.ihc.apirest.controllers;

import java.util.List;

import com.ihc.apirest.models.Empresa;
import com.ihc.apirest.models.Sucursal;
import com.ihc.apirest.service.EmpresaService;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.UsuarioService;

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

      boolean isExistenteEmpresa = empresaService.existeEmpresaByNit(empresa.getNit());

      //Se valida que el nit de la empresa no este registrado en la plataforma
      if(isExistenteEmpresa)
      {
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
      }

      /**
       * Se hace un set de empresa en todas las sucursales, ya que javascript no adminte estructuras ciclicas en el caso de [Sucursal] que contiene a [Empresa] 
       * y este a su vez contiene a [Sucursal], lo cual imposibilita enviar un entity de [Empresa] desde la App
       */
      for (Sucursal sucursal : empresa.getLstSucursales()) 
      {
        sucursal.setEmpresa(empresa);
      }

      //Validar si existe primero el nit de empresa
      empresaService.registrarEmpresa(empresa);

      return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
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
      /**
       * Se hace un set de empresa en todas las sucursales, ya que javascript no adminte estructuras ciclicas en el caso de [Sucursal] que contiene a [Empresa] 
       * y este a su vez contiene a [Sucursal], lo cual imposibilita enviar un entity de [Empresa] desde la App
       */
      for (Sucursal sucursal : empresa.getLstSucursales()) 
      {
        sucursal.setEmpresa(empresa);
      }
      
      empresaService.actualizarEmpresa(empresa);

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
   * Método que permite obtener una empresa según su id
   * @param idEmpresa Id de la empresa
   * @return Empresa encontrada
   */
  @GetMapping(value = "/empresas/{idEmpresa}")
  public ResponseEntity<Empresa> getEmpresa(@PathVariable("idEmpresa") Long idEmpresa)
  {
    try
    {
      Empresa empresa = empresaService.getEmpresaById(idEmpresa);
      
      if(null != empresa)
      {
        return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
      }
      
      return new ResponseEntity<Empresa>(HttpStatus.NO_CONTENT);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Empresa>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite obtener todas las empresas
   * @return Listado de empresas
   */
  @GetMapping(value = "/empresas")
  public ResponseEntity<List<Empresa>> getAllEmpresas()
  {
    try
    {
      List<Empresa> lstEmpresas = empresaService.getAllEmpresas();
      
      if(lstEmpresas.isEmpty())
      {
        return new ResponseEntity<List<Empresa>>(HttpStatus.NO_CONTENT);
      }
      
      return new ResponseEntity<List<Empresa>>(lstEmpresas, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Empresa>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



   /**
   * Método que permite obtener todas las empresas
   * @return Listado de empresas
   */
  @GetMapping(value = "/empresas/estados/{idEstado}")
  public ResponseEntity<List<Empresa>> getEmpresasByIdEstado(@PathVariable("idEstado") Long idEstado)
  {
    try
    {
      List<Empresa> lstEmpresas = empresaService.getEmpresasByIdEstado(idEstado);
      
      if(lstEmpresas.isEmpty())
      {
        return new ResponseEntity<List<Empresa>>(HttpStatus.NO_CONTENT);
      }
      
      return new ResponseEntity<List<Empresa>>(lstEmpresas, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<List<Empresa>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}