package com.claro.cpymes.dao;

import java.util.ArrayList;

import com.claro.cpymes.entity.ivr.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.ivr.AlarmaPymesServicioNitIVREntity;




public interface AlarmaPymesServicioNitIVRDAO {

   public ArrayList<AlarmaPymesServicioNitIVREntity> findByAlarm(AlarmaPymeIVREntity alarmaPymeIVR) throws Exception;

}
