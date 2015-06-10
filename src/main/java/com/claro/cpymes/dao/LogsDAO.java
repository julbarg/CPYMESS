package com.claro.cpymes.dao;

import java.util.ArrayList;

import com.claro.cpymes.entity.kou.LogEntity;


/**
 * Interface remota de LogsDAO
 * @author jbarragan
 *
 */
public interface LogsDAO {

   public LogEntity findOne(long id);

   public void create(LogEntity entity);

   public LogEntity update(LogEntity entity);

   public void delete(LogEntity entity);

   public void deleteById(long entityId);

   public ArrayList<LogEntity> findByEstado(String estado);
   
   public void updateList(ArrayList<LogEntity> listEntity);

}
