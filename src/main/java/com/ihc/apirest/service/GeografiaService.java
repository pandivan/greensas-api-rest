package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Geografia;
import com.ihc.apirest.repository.GeografiaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GeografiaService 
{
  @Autowired
  GeografiaRepository geografiaRepository;


  /**
   * Método que permite obtener todas las ciudades
   * @return Lista de ciudades
   */
  public List<Geografia> getAllCiudades() 
  {
    return geografiaRepository.findAll();
  }
}