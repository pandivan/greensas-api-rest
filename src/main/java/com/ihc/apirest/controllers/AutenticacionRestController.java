package com.ihc.apirest.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ihc.apirest.models.Cliente;
import com.ihc.apirest.service.ClienteService;
import com.ihc.apirest.service.JwtService;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AutenticacionRestController 
{
  @Autowired
  ClienteService clienteService;

  @Autowired
  JwtService jwtService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  BCryptPasswordEncoder bcrypt;



  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  

  /**
   * Método que permite crear un nuevo cliente en BD
   * @param cliente a crear
   * @return Cliente creado
   */
  @PostMapping(value="/signup")
  public ResponseEntity<String> signup(@RequestBody Cliente cliente)
  {
    try 
    {
      boolean isExistenteCliente = clienteService.existeClienteByCorreo(cliente.getCorreo());

      //Se valida que el correo del cliente no este registrado en la plataforma
      if(isExistenteCliente)
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

      cliente.setClave(bcrypt.encode(cliente.getClave()));

      //Este metodo creará un usuario en BD para la app de [mi-bario-app]
      Cliente clienteBD = clienteService.registrarCliente(cliente);
      
      String token = jwtService.generarToken(clienteBD);

      return new ResponseEntity<String>(token, HttpStatus.OK);
    } 
    catch (Exception e) 
    {
      return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Método que permite validar un cliente según su correo y clave
   * @param cliente que contiente el correo y clave a validar
   * @return Cliente encontrado
   */
  @PostMapping(value = "/login")
  public ResponseEntity<String> login(@RequestBody Cliente cliente) 
  {
    try
    {
      //El correo es el username de la aplicacion
      //Se valida autenticación por medio de correo y clave
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cliente.getCorreo(), cliente.getClave()));
        
      SecurityContextHolder.getContext().setAuthentication(authentication);
      
      Cliente clienteBD = (Cliente) authentication.getPrincipal();

      String token = jwtService.generarToken(clienteBD);

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



  /**
   * Método que permite restaurar temporalmente la clave de un cliente en BD
   * @param correo
   * @return
   */
  @PutMapping(value = "/restaurar")
  public ResponseEntity<Boolean> restaurarClave(@RequestBody String correo)
  {
    try
    {

      // String claveRandom = "1234";

      // claveRandom = bcrypt.encode(claveRandom);

      // clienteService.restaurarClave(claveRandom, correo);

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

