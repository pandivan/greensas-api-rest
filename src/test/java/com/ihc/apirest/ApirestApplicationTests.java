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
		cliente.setNombres("1");
		cliente.setCorreo("1@1.com");
		cliente.setCodProvincia("04");
		cliente.setCodCiudad("001");

		cliente.setClave(bcrypt.encode("pandi"));
		
		Cliente clienteBD = clienteRepository.save(cliente);
		
		assertTrue(clienteBD.getClave().equalsIgnoreCase(cliente.getClave()));
	}


	@Test
	void validarCliente() 
	{
		Cliente cliente = new Cliente();
		cliente.setCedula("1");
		cliente.setNombres("1");
		cliente.setCorreo("1@1.com");
		cliente.setCodProvincia("04");
		cliente.setCodCiudad("001");

		cliente.setClave("pandi");
		
		Cliente clienteBD = clienteRepository.findByCorreo(cliente.getCorreo());
		
		assertTrue(bcrypt.matches(cliente.getClave(), clienteBD.getClave()));
	}
}
