package com.ihc.apirest.repository;


import com.ihc.apirest.models.Sucursal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, String>
{
    Sucursal findByIdSucursal(Long idSucursal);
}