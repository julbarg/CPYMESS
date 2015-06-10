package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cpymes.entity.ivr.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.ivr.AlarmaPymesServicioNitIVREntity;




@Repository
public class AlarmaPymesServicioNitIVRDAOImpl extends TemplateIVRDAO<AlarmaPymesServicioNitIVREntity> implements
   AlarmaPymesServicioNitIVRDAO {

   @Override
   public ArrayList<AlarmaPymesServicioNitIVREntity> findByAlarm(AlarmaPymeIVREntity alarm) throws Exception {
      TypedQuery<AlarmaPymesServicioNitIVREntity> query = entityManager.createNamedQuery(
         "AlarmaPymesServicioNitIVREntity.findByAlarm", AlarmaPymesServicioNitIVREntity.class);
      query.setParameter("alarm", alarm);
      ArrayList<AlarmaPymesServicioNitIVREntity> results = (ArrayList<AlarmaPymesServicioNitIVREntity>) query
         .getResultList();

      return results;
   }

}
