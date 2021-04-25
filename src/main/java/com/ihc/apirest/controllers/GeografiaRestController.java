package com.ihc.apirest.controllers;

import java.util.List;

import com.ihc.apirest.models.Geografia;
import com.ihc.apirest.service.GeografiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class GeografiaRestController 
{
  @Autowired
  GeografiaService geografiaService;



  /**
   * Método que permite obtener todas las ciudades
   * @return Lista de ciudades
   */
  @GetMapping(value = "/ciudades")
  public ResponseEntity<List<Geografia>> getAllCiudades() 
  {
    try 
    {
      List<Geografia> lstCiudades = geografiaService.getAllCiudades();

      if(lstCiudades.isEmpty())
      {
        return new ResponseEntity<List<Geografia>>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<Geografia>>(lstCiudades, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<List<Geografia>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}