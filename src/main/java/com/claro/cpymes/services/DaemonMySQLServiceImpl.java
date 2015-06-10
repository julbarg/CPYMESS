package com.claro.cpymes.services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.claro.cpymes.services.remote.DaemonMySQLService;
import com.claro.cpymes.services.remote.ProcessService;


/**
 * Bean Singleton Asyncrono que inicia el Daemon.
 * Se encarga de ejecutar periodicamente el proceso
 * de filtrado y correlacion segun el tiempo configurado para ello
 * Si se ha configurado la opcion dameon
 * @author jbarragan
 *
 */
@Async
@Service
public class DaemonMySQLServiceImpl implements DaemonMySQLService {

   private static Logger LOGGER = LogManager.getLogger(DaemonMySQLServiceImpl.class.getName());

   private boolean procesar;

   @Autowired
   private ProcessService processService;

   @PostConstruct
   public void inicializar() {
      procesar = true;
   }

   /**
    * Inicalizador del daemon
    * @param timer Periodo de tiempo en el cual se ejecutara el proceso
    */
   public void inicializarDaemon(int timer) {
      try {
         LOGGER.info("Inicio Daemon");
         while (procesar) {
            procesarDB();
            Thread.sleep(timer);
         }

      } catch (final Exception e) {
         LOGGER.error("Error en el Daemon", e);
      }
   }

   /**
    * Ejecucion del proceso
    */
   private void procesarDB() {
      processService.procesar();
   }

   @PreDestroy
   public void clear() {
      procesar = false;
   }

}
