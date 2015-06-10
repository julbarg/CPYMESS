package com.claro.cpymes.services.remote;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.claro.cpymes.dto.AlarmPymesDTO;
import com.claro.cpymes.dto.PriorityCountDTO;

@Service
public interface CPYMESService {

   public ArrayList<AlarmPymesDTO> loadAlarm() throws Exception;

   public void update(AlarmPymesDTO alarmDTO) throws Exception;

   public PriorityCountDTO countAlarm(ArrayList<AlarmPymesDTO> listAlarm);

   public ArrayList<AlarmPymesDTO> loadAlarmByPriority(ArrayList<String> listPrioritySelect);

}
