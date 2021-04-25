package com.ihc.apirest.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ihc.apirest.models.Usuario;
import com.ihc.apirest.service.JwtService;
import com.ihc.apirest.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;





@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class UsuarioRestController 
{
  @Autowired
  UsuarioService usuarioService;

  @Autowired
  JwtService jwtService;

  @Autowired
  BCryptPasswordEncoder bcrypt;

  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  



  /**
   * Método que permite crear un nuevo usuario en BD
   * @param usuario a registrar
   * @return true si el usuario fue registrado exitosamente, en caso contrario false
   */
  @PostMapping(value="/usuarios")
  public ResponseEntity<Boolean> registrarUsuario(@RequestBody Usuario usuario)
  {
    try 
    {
      boolean isExistenteUsuario = usuarioService.existeUsuarioByUserName(usuario.getUsername());

      //Se valida que el userName del usuario no este registrado en la plataforma
      if(isExistenteUsuario)
      {
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
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
      usuarioService.registrarUsuario(usuario);

      return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }




  /**
   * Método que permite actualizar el password de un usuario en BD
   * @param nuevoPassword Nuevo password
   * @return true si el password del usuario fue actualizado, en caso contrario false
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/usuarios/passwords/{nuevoPassword}")
  public ResponseEntity<Boolean> actualizarPasswordUsuario(@RequestHeader("Authorization") String headerAuthorization, @PathVariable String nuevoPassword)
  {
    try 
    {
      String token = jwtService.getToken(headerAuthorization);
      String userName = jwtService.getUserNameFromToken(token);

      if(null != nuevoPassword)
      {
        Usuario usuario = new Usuario();
        usuario.setPassword(bcrypt.encode(nuevoPassword));
        usuario.setUserName(userName);
        
        usuarioService.actualizarPasswordUsuario(usuario);

        //Se retorna el usuario con el nuevo token
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
      }

      return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite actualizar el username del usuario y generar un nuevo token
   * @param nuevoUserName Username actualizar
   * @return Nuevo token si el usuario actualizó su username
   */
  // @PreAuthorize("hasRole('ROLE_ACUATEX_CLIENTE')")
  @PutMapping("/usuarios/usernames/{nuevoUserName}")
  public ResponseEntity<String> actualizarUserNameUsuario(@RequestHeader("Authorization") String headerAuthorization, @PathVariable String nuevoUserName)
  {
    try
    { 
      String token = jwtService.getToken(headerAuthorization);
      
      String userName = jwtService.getUserNameFromToken(token);
      

      if(usuarioService.existeUsuarioByUserName(nuevoUserName))
      {
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
      }
      
      Usuario usuario = new Usuario();
      usuario.setUserName(userName);
      usuario.setNuevoUserName(nuevoUserName);

      usuarioService.actualizarUserNameUsuario(usuario);

      //Al actualizar el userName estamos cambiando el username de la aplicación, recordar que este username esta impreso en el token, es por eso que debemos genear un token nuevo
      token = jwtService.generarToken(usuario);

      //Se retorna el nuevo token
      return new ResponseEntity<String>(token, HttpStatus.OK);
    }
    catch(DataIntegrityViolationException dive)
    {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite restaurar el password de un usuario en BD
   * @param userName representa el usuario que contiene el email, al cual se enviarán las instrucciones para restaurar el password
   * @return true si se envió el correo correctamente, en caso contrario false
   */
  // @PutMapping(value = "/usuarios/{username}")
  public ResponseEntity<Boolean> restaurarPassword(@PathVariable("username") String username)
  {
    try
    {
      // String passwordRandom = "1234";

      // passwordRandom = bcrypt.encode(passwordRandom);

      // usuarioService.restaurarPassword(passwordRandom, userName);

      // String YOUR_DOMAIN_NAME = "sandboxa3bb8428392a4b859f2af588ec5feb87.mailgun.org";
      String API_KEY = "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";

      ApiClient apiClient = Configuration.getDefaultApiClient();
        
      // Configure API key authorization: api-key
      ApiKeyAuth apiKey = (ApiKeyAuth) apiClient.getAuthentication("api-key");
      apiKey.setApiKey(API_KEY);
      
      
      List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
      SendSmtpEmailTo to = new SendSmtpEmailTo();
      to.setEmail("pandivan@hotmail.com");
      // to.setName("Rana"); quitarlo tambien de la plantilla
      toList.add(to);
    
      Properties params = new Properties();
      params.setProperty("ORDER", "000007");
      params.setProperty("DATE", "19-03-2021");
      
      SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
      sendSmtpEmail.setTo(toList);
      sendSmtpEmail.setParams(params);
      sendSmtpEmail.setTemplateId(1L);

      TransactionalEmailsApi api = new TransactionalEmailsApi();
      CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);

      System.out.println(response.toString());
      
      return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    catch (Exception e) 
    {
      return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

