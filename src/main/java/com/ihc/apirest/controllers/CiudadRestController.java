package com.ihc.apirest.controllers;

import java.util.List;

import com.ihc.apirest.models.Ciudad;
import com.ihc.apirest.service.CiudadService;

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
public class CiudadRestController 
{
  @Autowired
  CiudadService ciudadService;



  /**
   * Método que permite obtener todas las ciudades
   * @return Lista de ciudades
   */
  @GetMapping(value = "/ciudades")
  public ResponseEntity<List<Ciudad>> getAllCiudades() 
  {
    try 
    {
      List<Ciudad> lstCiudades = ciudadService.getAllCiudades();

      return new ResponseEntity<List<Ciudad>>(lstCiudades, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<List<Ciudad>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}