package com.ihc.apirest.repository;

import com.ihc.apirest.models.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String>
{
    Empresa findByIdEmpresa(Long idEmpresa);

    boolean existsByNit(String nit);
}