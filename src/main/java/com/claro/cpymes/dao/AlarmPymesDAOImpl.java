package com.claro.cpymes.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.entity.pymes.AlarmPymesEntity;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * AlarmPymesDAO - DAO que controla las transaciones a base 
 * de datos de la entidad AlarmPymesEntity
 * @author jbarragan
 *
 */
@Repository
public class AlarmPymesDAOImpl extends TemplateCPYMESDAO<AlarmPymesEntity> implements AlarmPymesDAO {

   private static Logger LOGGER = LogManager.getLogger(AlarmPymesDAOImpl.class.getName());

   /**
    * Obtiene las entidades AlarmPymesEntity por estado
    * @param estado Con el que se realiza la consulta
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findByEstado(String estado) {
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByEstado",
         AlarmPymesEntity.class);
      query.setParameter("estado", estado);
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.setMaxResults(
         Constant.MAXIME_RESULT_ALARM).getResultList();

      return results;
   }

   /**
    * Busqueda de alarmas pot prioridad
    * @param listPrioritySelect Lista de prioridades a consultar
    * @return ArrayList<AlarmPymesEntity>  Lista de alarmas encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findByPriority(ArrayList<String> listPrioritySelect) {
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByPriority",
         AlarmPymesEntity.class);
      query.setParameter("listPriority", listPrioritySelect);
      query.setParameter("estado", ProcessEnum.ACTIVO.getValue());
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.getResultList();

      return results;
   }

   /**
    * Persiste la entidad
    * @param entity Entidad a persistir
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public void create(AlarmPymesEntity entity) {
      try {
         super.create(entity);
      } catch (Exception e) {
         LOGGER.error("Error creando registro", e);
      }

   }

   @Override
   @Transactional(MANAGER_CPYMES)
   public void createList(ArrayList<AlarmPymesEntity> listAlarm) {
      for (AlarmPymesEntity alarm : listAlarm) {
         entityManager.persist(alarm);
      }

   }

   /**
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda
    * @param eventName Nombre del evento
    * @param name Nombre del dispositivo
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findSimiliar(String eventName, String name, Date startDate, Date endDate) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliar",
            AlarmPymesEntity.class);
         query.setParameter("eventName", eventName);
         query.setParameter("name", name);
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", endDate);
         results = (ArrayList<AlarmPymesEntity>) query.setFirstResult(1).setMaxResults(1).getResultList();

      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return results;
      }

      return results;
   }

   /**
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda, con estado ACTIVO
    * @param nodo Nodo
    * @param nameCorrelation Nombre de la Correlacion
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date startDate, Date endDate) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliarCEP",
            AlarmPymesEntity.class);
         query.setParameter("nodo", nodo);
         query.setParameter("nameCorrelation", nameCorrelation);
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", endDate);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.getResultList();
      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return results;
      }

      return results;
   }

   /**
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda, con estado ACTIVO
    * @param nodo Nodo
    * @param nameCorrelation Nombre de la Correlacion
    * @param date Date
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date date) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliarCEPByDate",
            AlarmPymesEntity.class);
         query.setParameter("nodo", nodo);
         query.setParameter("nameCorrelation", nameCorrelation);
         query.setParameter("date", date);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.setFirstResult(1).setMaxResults(1).getResultList();

      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return results;
      }

      return results;
   }

   /**
    * Obtiene las entidades AlarmPymesEntity reconocidas. Estado RECONOCIDO
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @Transactional(MANAGER_CPYMES)
   public ArrayList<AlarmPymesEntity> findSimiliarCEPReconocidas(Date startDate, Date endDate) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery(
            "AlarmPymesEntity.findSimiliarCEPReconocidas", AlarmPymesEntity.class);
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", endDate);
         query.setParameter("estado", ProcessEnum.RECONOCIDO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.getResultList();

      } catch (Exception e) {
         LOGGER.error("Error buscando registros correlacionados reconocidos", e);
         return results;
      }
      return results;
   }

   @Transactional(MANAGER_CPYMES)
   public ArrayList<LogDTO> validateSimilar(ArrayList<LogDTO> listLogsDTO) {
      ArrayList<AlarmPymesEntity> listResult = new ArrayList<AlarmPymesEntity>();
      Date endDate;
      Date startDate;
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliar",
            AlarmPymesEntity.class);
         for (LogDTO log : listLogsDTO) {
            if (log.isRelevant()) {
               endDate = new Date();
               startDate = Util.restarFecha(endDate, Integer.parseInt(Constant.TIMER_SIMILAR_REGISTERS));
               query.setParameter("eventName", log.getNameEvent());
               query.setParameter("name", log.getName());
               query.setParameter("startDate", startDate);
               query.setParameter("endDate", endDate);
               listResult = (ArrayList<AlarmPymesEntity>) query.setMaxResults(1).getResultList();
               if (listResult.size() > 0) {
                  log.setRelevant(false);
               }
            }
         }
      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return listLogsDTO;
      }

      return listLogsDTO;
   }

}
