package com.claro.cpymes.services;

import org.springframework.stereotype.Service;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.services.remote.LoginService;


/**
 * Bean que controla la autenticacion al Sistema
 * @author jbarragan
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {

   private static final String DOMAIN_NAME = "co.attla.corp";

   @Override
   public boolean authenticate(UserDTO user) throws Exception {
      // Test
      return true;

      // boolean logIn;
      // LDAPAuthenticationServicesServiceLocator ldapL = new LDAPAuthenticationServicesServiceLocator();
      // LDAPAuthenticationServices query = ldapL.getLDAPAuthenticationServices();
      // logIn = query.userAuthentication(user.getUserName(), user.getPassword(), DOMAIN_NAME);
      //
      // return logIn;

   }

}
