package com.ihc.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{
  @Autowired
  private ClienteService clienteService;



  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
  {
    //El correo es el username de la aplicacion
    return clienteService.getClienteByCorreo(userName);
  }
}
