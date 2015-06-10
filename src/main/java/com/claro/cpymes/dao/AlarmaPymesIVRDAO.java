package com.claro.cpymes.dao;

import java.util.ArrayList;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.ivr.AlarmaPymeIVREntity;




public interface AlarmaPymesIVRDAO {

   public ArrayList<AlarmaPymeIVREntity> findByEstado(String estado);

   public AlarmaPymeIVREntity findOne(long id);

   public AlarmaPymeIVREntity update(AlarmaPymeIVREntity alarmaPymeIVR);

   public AlarmaPymeIVREntity findById(long idAlarmaPymes) throws Exception;

   public ArrayList<AlarmaPymeIVREntity> findByFilter(AlarmaPymeIVRDTO filterAlarm) throws Exception;

}
