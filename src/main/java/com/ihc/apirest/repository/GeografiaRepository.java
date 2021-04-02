package com.ihc.apirest.repository;

import com.ihc.apirest.models.Geografia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GeografiaRepository extends JpaRepository<Geografia, String> 
{

}