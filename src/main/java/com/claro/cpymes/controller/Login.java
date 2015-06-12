package com.claro.cpymes.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.services.remote.LoginService;
import com.claro.cpymes.util.Messages;
import com.claro.cpymes.util.Util;


/**
 * Controla la vista login.xhtml
 * @author jbarragan
 *
 */

@Controller("loginView")
@Scope("session")
public class Login {

   private static Logger LOGGER = LogManager.getLogger(Login.class.getName());

   private UserDTO user;

   @Autowired
   private LoginService loginService;

   private static final String URL_LOGIN = "login.xhtml";

   @Autowired
   private IVR ivr;

   @PostConstruct
   public void initialize() {
      user = new UserDTO();
      FacesContext.getCurrentInstance().getExternalContext().getSession(true);
   }

   /**
    * Verifica autenticacion
    * @return Redireccion de pagina
    */
   public boolean authenticate() {
      try {
         LOGGER.info("AUTENTICAR - IVR");
         if (loginService.authenticate(user)) {
            Util.iniciarSesion(user);
            ivr.initial();
            return true;
         }
         Util.addMessageFatal(Messages.AUTHENTICATION_ERROR);
         return false;

      } catch (Exception e) {
         LOGGER.error(Messages.AUTHENTICATION_ERROR, e);
         Util.addMessageFatal(Messages.AUTHENTICATION_ERROR);
         return false;
      }

   }

   public void validateSession() {
      try {
         Util.getUserName();
      } catch (Exception e) {
         goLogIn();
      }
   }

   public void closeSession() {
      goLogIn();
      Util.logout();
      user = new UserDTO();
   }

   public void goLogIn() {
      try {
         Util.redirect(URL_LOGIN);
      } catch (IOException e) {
      }
   }

   public UserDTO getUser() {
      return user;
   }

   public void setUser(UserDTO user) {
      this.user = user;
   }

   public LoginService getLoginService() {
      return loginService;
   }

   public void setLoginService(LoginService loginService) {
      this.loginService = loginService;
   }

   public IVR getIvr() {
      return ivr;
   }

   public void setIvr(IVR ivr) {
      this.ivr = ivr;
   }

}
