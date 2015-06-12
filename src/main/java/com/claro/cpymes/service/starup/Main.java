package com.claro.cpymes.service.starup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.claro.cpymes.services.remote.DaemonMySQLService;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * Bean Singleton StartUp 
 * Se ejecuta cuando se inicia la aplicacion
 * Es el encargado de ejecutar los Bean de proceso de 
 * acuerdo al metodo configurado
 * @author jbarragan
 *
 */
@Configuration
@EnableAsync
public class Main implements InitializingBean {

   private static Logger LOGGER = LogManager.getLogger(Main.class.getName());

   @Autowired
   private DaemonMySQLService daemonMySQLService;

   @Override
   @Async
   public void afterPropertiesSet() throws Exception {
      init();
   }

   @Async
   public void init() {
      String method = Util.getProperties(Constant.METHOD);
      if (method.equals(Constant.METHOD_DAEMON)) {
         LOGGER.info("CPyMES Iniciado con el metodo: " + Constant.METHOD_DAEMON);
         daemonMySQLService.inicializarDaemon(Constant.TIMER_DAEMON);

      } else {
         LOGGER.error("Metodo de Ejecución no encontrado");
         LOGGER.info("Revisar el archivo CPyMES.properties: " + Constant.PATH_CONFIG_PROPERTIES + ". Propiedad: "
            + Constant.METHOD);
         LOGGER.info("Valores permitidos: " + Constant.METHOD_DAEMON + ", " + Constant.METHOD_LISTENER);
      }
   }

}
