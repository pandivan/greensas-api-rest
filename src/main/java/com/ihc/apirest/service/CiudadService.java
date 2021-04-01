package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Ciudad;
import com.ihc.apirest.repository.CiudadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CiudadService 
{
  @Autowired
  CiudadRepository ciudadRepository;


  /**
   * Método que permite obtener todas las ciudades
   * @return Lista de ciudades
   */
  public List<Ciudad> getAllCiudades() 
  {
    return ciudadRepository.getAllCiudades();
  }
}