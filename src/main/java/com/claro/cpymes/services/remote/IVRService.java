package com.claro.cpymes.services.remote;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.ivr.AlarmaPymesServicioNitIVREntity;


@Service
public interface IVRService {

   public ArrayList<AlarmaPymeIVRDTO> findAllAlarmIVR() throws Exception;

   public void edit(AlarmaPymeIVRDTO alarmEdit) throws Exception;

   public ArrayList<AlarmaPymesServicioNitIVREntity> findCodigosServicio(AlarmaPymeIVRDTO alarmFind) throws Exception;

   public ArrayList<AlarmaPymeIVRDTO> findByFilter(AlarmaPymeIVRDTO alarmaFilter) throws Exception;

   public Date getDateLoadNits();

}
