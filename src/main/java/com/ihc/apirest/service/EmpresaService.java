package com.ihc.apirest.service;

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
   * @param idEmpresa con el cual se buscara el empresa en BD
   * @return Empresa encontrado
   */
  public Empresa getEmpresaById(Long idEmpresa) 
  {
    return empresaRepository.findById(idEmpresa);
  }



  /**
   * Método que permite registrar una empresa en BD
   * @param empresa a registrar
   * @return Empresa registrada
   */
  public Empresa registrarEmpresa(Empresa empresa) 
  {
    return empresaRepository.save(empresa);
  }



  /**
   * Método que permite actualizar una empresa en BD
   * @param empresa actualizar
   * @return Empresa actualizada
   */
  public Empresa actualizarEmpresa(Empresa empresa) 
  {
    return empresaRepository.save(empresa);
  }
}
