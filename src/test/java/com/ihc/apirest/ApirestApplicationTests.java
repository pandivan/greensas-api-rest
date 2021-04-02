package com.ihc.apirest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.repository.ClienteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ApirestApplicationTests 
{
	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Test
	void registrarCliente() 
	{
		Cliente cliente = new Cliente();
		cliente.setCedula("1");
		cliente.setNombre("1");
		cliente.setEmail("1@1.com");
		cliente.setPassword(bcrypt.encode("pandi"));
		
		Cliente clienteBD = clienteRepository.save(cliente);
		
		assertTrue(clienteBD.getPassword().equalsIgnoreCase(cliente.getPassword()));
	}


	@Test
	void validarCliente() 
	{
		Cliente cliente = new Cliente();
		cliente.setCedula("1");
		cliente.setNombre("1");
		cliente.setEmail("1@1.com");
		cliente.setPassword("pandi");
		
		Cliente clienteBD = clienteRepository.findByEmail(cliente.getEmail());
		
		assertTrue(bcrypt.matches(cliente.getPassword(), clienteBD.getPassword()));
	}
}
