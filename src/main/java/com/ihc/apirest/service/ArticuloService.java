package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Articulo;
import com.ihc.apirest.repository.ArticuloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ArticuloService 
{
  @Autowired
  ArticuloRepository articuloRepository;

  
  /**
   * Método que permite obtener todos los articulos con sus categorías
   * @return Lista de articulos
   */
  public List<Articulo> getAllArticulos() 
  {
    return articuloRepository.getAllArticulos();
  }
}
