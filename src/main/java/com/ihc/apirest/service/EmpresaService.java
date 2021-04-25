package com.ihc.apirest.service;

import java.util.List;

import com.ihc.apirest.models.Empresa;
import com.ihc.apirest.repository.EmpresaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class EmpresaService 
{
  @Autowired
  private EmpresaRepository empresaRepository;


  
  /**
   * Método que permite obtener una empresa según su Id
   * @param idEmpresa con el cual se buscara el empresa
   * @return Empresa encontrado
   */
  public Empresa getEmpresaById(Long idEmpresa) 
  {
    return empresaRepository.findByIdEmpresa(idEmpresa);
  }



  /**
   * Método que permite registrar una empresa
   * @param empresa a registrar
   * @return Empresa registrada
   */
  public Empresa registrarEmpresa(Empresa empresa) 
  {
    return empresaRepository.save(empresa);
  }



  /**
   * Método que permite actualizar una empresa
   * @param empresa actualizar
   * @return Empresa actualizada
   */
  public Empresa actualizarEmpresa(Empresa empresa) 
  {
    return empresaRepository.save(empresa);
  }



  /**
   * Método que permite validar si la empresa existe en bd
   * @param nit a valdiar
   * @return true si existe la empresa, en caso contrario false
   */
  public boolean existeEmpresaByNit(String nit) 
  {
    return empresaRepository.existsByNit(nit);
  }



  /**
   * Método que permite otener todas las empresas sin importar el estado
   * @return Listado de empresas
   */
  public List<Empresa> getAllEmpresas()
  {
    return empresaRepository.findAll();
  }




  /**
   * Método que permite otener todas las empresas según el estado
   * @param idEstado Id del estado
   * @return Listado de empresas
   */
  public List<Empresa> getEmpresasByIdEstado(Long idEstado)
  {
    return empresaRepository.findByIdEstado(idEstado);
  }
}
