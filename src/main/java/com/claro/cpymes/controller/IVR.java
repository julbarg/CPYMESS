package com.claro.cpymes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.ivr.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.services.remote.IVRService;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * Controla la vista cpymes.xhtml
 * @author jbarragan
 *
 */
@Controller("ivr")
@Scope("session")
public class IVR {

   private static Logger LOGGER = LogManager.getLogger(IVR.class.getName());

   private static final int INTERVAL = 30;

   private static final String URL_LOGIN = "login.xhtml";

   private boolean viewPause;

   private boolean viewPlay;

   private int interval;

   @Autowired
   private IVRService IVRService;

   private ArrayList<AlarmaPymeIVRDTO> listAlarmaPymesIVR;

   private ArrayList<AlarmaPymesServicioNitIVREntity> listCodigosServicio;

   private AlarmaPymeIVRDTO alarmFilter;

   private AlarmaPymeIVRDTO alarmEdit;

   @PostConstruct
   public void initial() {
      load();
      play();
   }

   public void load() {
      try {
         validateDateLoadNits();
         initializeAttributes();
         if (!validateSesion()) {
            goLogIn();
            return;
         }
         listAlarmaPymesIVR = IVRService.findAllAlarmIVR();
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar alarmas del IVR", e);
      }
   }

   private void validateDateLoadNits() {
      Date dateLoadNits = IVRService.getDateLoadNits();
      Date today = new Date();
      long hours;
      if (dateLoadNits != null) {
         hours = Util.getHoursBetweenTwoDates(dateLoadNits, today);
         if (hours > Constant.TIMER_LOAD_NITS) {
            Util.addMessageFatal("Se esta presentando un error al realizar el cargue de Codigos de Servicio vs Nits. "
               + hours);
            LOGGER.info("Se esta presentando un error al realizar el cargue de Codigos de Servicio vs Nits.");
         }
      }

   }

   public void initializeAttributes() {
      alarmFilter = new AlarmaPymeIVRDTO();
      listCodigosServicio = new ArrayList<AlarmaPymesServicioNitIVREntity>();
      listAlarmaPymesIVR = new ArrayList<AlarmaPymeIVRDTO>();
   }

   public void view() {
      if (!validateSesion()) {
         goLogIn();
         return;
      }
      try {
         listCodigosServicio = IVRService.findCodigosServicio(alarmEdit);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar codigos de servicios del IVR", e);
      }
   }

   public void edit() {
      if (!validateSesion()) {
         goLogIn();
         return;
      }
      try {
         IVRService.edit(alarmEdit);
         Util.addMessageInfo("Alarma modificada exitosamente");
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al modificar alarmas del IVR", e);
      }
   }

   public void find() {
      if (!validateSesion()) {
         goLogIn();
         return;
      }
      LOGGER.info("find View");
      try {
         listAlarmaPymesIVR = IVRService.findByFilter(alarmFilter);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al consultar alarmas del IVR", e);
      }
   }

   public void goLogIn() {
      try {
         Util.redirect(URL_LOGIN);
      } catch (IOException e) {
      }
   }

   public boolean goCpymes() {
      return true;
   }

   public boolean validateSesion() {
      try {
         Util.getUserName();
         return true;
      } catch (Exception e) {
         LOGGER.error("Error de Sesion", e);
         return false;

      }
   }

   /**
    * Pausa la ejecucion periodica de refresh
    */
   public void pause() {
      setViewPlay(true);
      setViewPause(false);
      setInterval(INTERVAL * 1000);
   }

   /**
    * Reanuda la ejecucion periodica de refresh
    */
   public void play() {
      setViewPlay(false);
      setViewPause(true);
      setInterval(INTERVAL);
   }

   public ArrayList<AlarmaPymeIVRDTO> getListAlarmaPymesIVR() {
      return listAlarmaPymesIVR;
   }

   public void setListAlarmaPymesIVR(ArrayList<AlarmaPymeIVRDTO> listAlarmaPymesIVR) {
      this.listAlarmaPymesIVR = listAlarmaPymesIVR;
   }

   public AlarmaPymeIVRDTO getAlarmEdit() {
      return alarmEdit;
   }

   public void setAlarmEdit(AlarmaPymeIVRDTO alarmEdit) {
      this.alarmEdit = alarmEdit;
   }

   public ArrayList<AlarmaPymesServicioNitIVREntity> getListCodigosServicio() {
      return listCodigosServicio;
   }

   public void setListCodigosServicio(ArrayList<AlarmaPymesServicioNitIVREntity> listCodigosServicio) {
      this.listCodigosServicio = listCodigosServicio;
   }

   public AlarmaPymeIVRDTO getAlarmFilter() {
      return alarmFilter;
   }

   public void setAlarmFilter(AlarmaPymeIVRDTO alarmFilter) {
      this.alarmFilter = alarmFilter;
   }

   public boolean isViewPause() {
      return viewPause;
   }

   public void setViewPause(boolean viewPause) {
      this.viewPause = viewPause;
   }

   public boolean isViewPlay() {
      return viewPlay;
   }

   public void setViewPlay(boolean viewPlay) {
      this.viewPlay = viewPlay;
   }

   public int getInterval() {
      return interval;
   }

   public void setInterval(int interval) {
      this.interval = interval;
   }

}
