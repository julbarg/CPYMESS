package com.claro.cpymes.services;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cpymes.dao.AlarmaPymesIVRDAO;
import com.claro.cpymes.dao.AlarmaPymesServicioNitIVRDAO;
import com.claro.cpymes.dao.ParameterDAO;
import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.ivr.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.ivr.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.services.remote.IVRService;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


@Service
public class IVRServiceImpl implements IVRService {

   private static Logger LOGGER = LogManager.getLogger(IVRServiceImpl.class.getName());

   @Autowired
   private AlarmaPymesIVRDAO alarmPymesIVRDAO;

   @Autowired
   private AlarmaPymesServicioNitIVRDAO alarmaPymesServicioNitIVRDAO;

   @Autowired
   private ParameterDAO parameterDAO;

   @PostConstruct
   private void initialize() {

   }

   public ArrayList<AlarmaPymeIVRDTO> findAllAlarmIVR() throws Exception {
      ArrayList<AlarmaPymeIVRDTO> listAlarmDTO = new ArrayList<AlarmaPymeIVRDTO>();
      ArrayList<AlarmaPymeIVREntity> listAlarmEntity = alarmPymesIVRDAO.findByEstado(StateEnum.ACTIVO.getValue());
      for (AlarmaPymeIVREntity alarmEntity : listAlarmEntity) {
         AlarmaPymeIVRDTO alarmDTO = mapearEntity(alarmEntity);
         listAlarmDTO.add(alarmDTO);
      }
      return listAlarmDTO;

   }

   @Override
   public void edit(AlarmaPymeIVRDTO alarmEdit) throws Exception {
      AlarmaPymeIVREntity alarmEntity = alarmPymesIVRDAO.findById(alarmEdit.getIdAlarmaPymes());
      alarmEntity.setFechaEsperanza(alarmEdit.getFechaEsperanza());
      alarmEntity.setTicketOnix(alarmEdit.getTicketOnix());
      alarmPymesIVRDAO.update(alarmEntity);
   }

   @Override
   public ArrayList<AlarmaPymesServicioNitIVREntity> findCodigosServicio(AlarmaPymeIVRDTO alarmFind) throws Exception {
      AlarmaPymeIVREntity alarmEntity = alarmPymesIVRDAO.findById(alarmFind.getIdAlarmaPymes());
      return alarmaPymesServicioNitIVRDAO.findByAlarm(alarmEntity);

   }

   public ArrayList<AlarmaPymeIVRDTO> findByFilter(AlarmaPymeIVRDTO alarmaFilter) throws Exception {
      ArrayList<AlarmaPymeIVREntity> listAlarmEntity = alarmPymesIVRDAO.findByFilter(alarmaFilter);
      ArrayList<AlarmaPymeIVRDTO> listAlarmDTO = new ArrayList<AlarmaPymeIVRDTO>();
      for (AlarmaPymeIVREntity alarmEntity : listAlarmEntity) {
         AlarmaPymeIVRDTO alarmDTO = mapearEntity(alarmEntity);
         listAlarmDTO.add(alarmDTO);
      }
      return listAlarmDTO;
   }

   private AlarmaPymeIVRDTO mapearEntity(AlarmaPymeIVREntity alarmEntity) {
      AlarmaPymeIVRDTO alarmDTO = new AlarmaPymeIVRDTO();

      alarmDTO.setCiudad(alarmEntity.getCiudad());
      alarmDTO.setClaseEquipo(alarmEntity.getClaseEquipo());
      alarmDTO.setCodigoAudioIvr(alarmEntity.getCodigoAudioIvr());
      alarmDTO.setDescripcionAlarma(alarmEntity.getDescripcionAlarma());
      alarmDTO.setDivision(alarmEntity.getDivision());
      alarmDTO.setEstadoAlarma(alarmEntity.getEstadoAlarma());
      alarmDTO.setFechaEsperanza(alarmEntity.getFechaEsperanza());
      alarmDTO.setFechaFin(alarmEntity.getFechaFin());
      alarmDTO.setFechaInicio(alarmEntity.getFechaInicio());
      alarmDTO.setFechaModificacion(alarmEntity.getFechaModificacion());
      alarmDTO.setIdAlarmaPymes(alarmEntity.getIdAlarmaPymes());
      alarmDTO.setIp(alarmEntity.getIp());
      alarmDTO.setTicketOnix(alarmEntity.getTicketOnix());
      alarmDTO.setTiempoTotalFalla(alarmEntity.getTiempoTotalFalla());
      alarmDTO.setTipoEvento(alarmEntity.getTipoEvento());
      alarmDTO.setUsuarioModificacion(alarmEntity.getUsuarioModificacion());

      return alarmDTO;
   }

   @Override
   public Date getDateLoadNits() {
      Date date;
      try {
         String dateString = parameterDAO.findByName(Constant.FECHA_ULTIMO_CARGUE_NITS);
         date = Util.getDateStringToDate(dateString);
         return date;
      } catch (Exception e) {
         LOGGER.error("getDateLoadNits", e);
         return null;
      }

   }
}
