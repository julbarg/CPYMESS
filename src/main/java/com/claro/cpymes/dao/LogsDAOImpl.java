package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cpymes.entity.kou.LogEntity;
import com.claro.cpymes.util.Constant;


/**
 * LogsDAO - DAO que controla las transaciones a base 
 * de datos de la entidad LogEntity
 * @author jbarragan
 *
 */
@Repository
public class LogsDAOImpl extends TemplateKOUDAO<LogEntity> implements LogsDAO {

   private static Logger LOGGER = LogManager.getLogger(LogsDAOImpl.class.getName());

   /**
    * Obtiene las entidades LogEntity por estado
    * @param procesado Filtro con el que se realiza la consulta
    * @return ArrayList<LogEntity> Lista de entidades encontradas
    */
   @Override
   public ArrayList<LogEntity> findByEstado(String procesado) {
      TypedQuery<LogEntity> query = entityManager.createNamedQuery("LogEntity.findByProcesado", LogEntity.class);
      query.setParameter("procesados", procesado);
      ArrayList<LogEntity> results = (ArrayList<LogEntity>) query.setMaxResults(Constant.MAXIME_RESULT_LOGS)
         .getResultList();

      return results;

   }

   /**
    * Persiste la entidad
    * @param entity Entidad a persistir
    */
   @Override
   @Transactional(MANAGER_KOU)
   public LogEntity update(LogEntity entity) {
      LogEntity logEntity = new LogEntity();
      try {
         logEntity = super.update(entity);
      } catch (Exception e) {
         LOGGER.error("Error actualizando registro: " + e);
      }

      return logEntity;
   }

   @Override
   @Transactional(MANAGER_KOU)
   public void updateList(ArrayList<LogEntity> listEntity) {
      for (LogEntity log : listEntity) {
         entityManager.merge(log);
      }
   }

}
