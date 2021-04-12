package com.ihc.apirest.controllers;

import java.text.SimpleDateFormat;

import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class AutenticacionRestController 
{
  @Autowired
  UsuarioService usuarioService;

  @Autowired
  JwtService jwtService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  BCryptPasswordEncoder bcrypt;

  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  




  /**
   * Método que permite crear un nuevo usuario en BD
   * @param usuario a crear
   * @return Usuario creado
   */
  @PostMapping(value="/signup")
  public ResponseEntity<String> signup(@RequestBody Usuario usuario)
  {
    try 
    {
      boolean isExistenteUsuario = usuarioService.existeUsuarioByUserName(usuario.getUsername());

      //Se valida que el userName del usuario no este registrado en la plataforma
      if(isExistenteUsuario)
      {
        return new ResponseEntity<String>(HttpStatus.CREATED);
      }

      /**
        Habilitar las siguientes lineas en el caso que se necesite registrar un usuario con ROLES...
        

        // Se crea un array o set de roles con el fin de agregar el nuevo rol que se asigno desde la web,
        // Set<Rol> roles = new HashSet<>();

        // Se crea al usuario con el ROL por defecto ROLE_USER
        // roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
          
        // Se valida si el nuevo usuario tiene el rol ADMIN para agregarlo al Set<> de roles, de lo contrario se crea con el ROL por defecto ROLE_USER
        // if(nuevoUsuario.getRoles().contains("admin"))
        // {
        //   roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        // }
        
        // usuario.setRoles(roles);
       **/

      usuario.setPassword(bcrypt.encode(usuario.getPassword()));

      //Este metodo creará un usuario en BD para la app de [mi-bario-app]
      Usuario usuarioBD = usuarioService.registrarUsuario(usuario);
      
      String token = jwtService.generarToken(usuarioBD);

      return new ResponseEntity<String>(token, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite validar un usuario según su userName y password
   * @param usuario que contiente el userName y password a validar
   * @return Usuario encontrado
   */
  @PostMapping(value = "/login")
  public ResponseEntity<String> login(@RequestBody Usuario usuario) 
  {
    try
    {
      //El userName es el username de la aplicacion
      //Se valida autenticación por medio de userName y password
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        
      SecurityContextHolder.getContext().setAuthentication(authentication);
      
      Usuario usuarioBD = (Usuario) authentication.getPrincipal();

      String token = jwtService.generarToken(usuarioBD);

      return new ResponseEntity<String>(token, HttpStatus.OK);
    }
    catch (BadCredentialsException bce) 
    {
      return new ResponseEntity<String>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite validar el token
   * @return True si es valido, en caso contrario False
   */
  @GetMapping(value = "/info")
  public ResponseEntity<Boolean> validarToken() 
  {
    try
    {
      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}