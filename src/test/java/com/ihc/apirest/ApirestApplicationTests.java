package com.ihc.apirest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ApirestApplicationTests 
{
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Test
	void registrarUsuario() 
	{
		Usuario usuario = new Usuario();
		usuario.setUserName("1@1.com");
		usuario.setPassword(bcrypt.encode("pandi"));
		
		Usuario usuarioBD = usuarioRepository.save(usuario);
		
		assertTrue(usuarioBD.getPassword().equalsIgnoreCase(usuario.getPassword()));
	}


	@Test
	void validarUsuario() 
	{
		Usuario usuario = new Usuario();
		usuario.setUserName("1@1.com");
		usuario.setPassword("pandi");
		
		Usuario usuarioBD = usuarioRepository.findByUserName(usuario.getUsername());
		
		assertTrue(bcrypt.matches(usuario.getPassword(), usuarioBD.getPassword()));
	}
}
