package com.ihc.apirest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(schema="dimension")
public class Geografia 
{
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGeografia;
	private String idPais;
  private String pais;
  private String departamento;
  private String ciudad;
}
