package com.ihc.apirest.service;

import com.ihc.apirest.models.Sucursal;
import com.ihc.apirest.repository.SucursalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class SucursalService 
{
  @Autowired
  private SucursalRepository sucursalRepository;


  
  /**
   * Método que permite obtener una sucursal según su Id
   * @param idSucursal con el cual se buscara el sucursal
   * @return Sucursal encontrado
   */
  public Sucursal getSucursalById(Long idSucursal) 
  {
    return sucursalRepository.findByIdSucursal(idSucursal);
  }



  /**
   * Método que permite registrar una sucursal
   * @param sucursal a registrar
   * @return Sucursal registrada
   */
  public Sucursal registrarSucursal(Sucursal sucursal) 
  {
    return sucursalRepository.save(sucursal);
  }



  /**
   * Método que permite actualizar una sucursal
   * @param sucursal actualizar
   * @return Sucursal actualizada
   */
  public Sucursal actualizarSucursal(Sucursal sucursal) 
  {
    return sucursalRepository.save(sucursal);
  }
}
