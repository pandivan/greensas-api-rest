package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Producto;
import com.ihc.apirest.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductoService 
{
  @Autowired
  ProductoRepository productoRepository;

  
  /**
   * Método que permite obtener todos los productos con sus categorías
   * @return Lista de productos
   */
  public List<Producto> getAllProductos() 
  {
    return productoRepository.findAll();
  }
}
