package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cpymes.entity.pymes.AlarmCatalogEntity;
import com.claro.cpymes.util.Constant;


@Repository
public class AlarmCatalogDAOImpl extends TemplateCPYMESDAO<AlarmCatalogEntity> implements AlarmCatalogDAO {

   @Override
   public ArrayList<AlarmCatalogEntity> findByFilter(String filter) throws Exception {
      TypedQuery<AlarmCatalogEntity> query = entityManager.createNamedQuery("AlarmCatalogEntity.findByFilter",
         AlarmCatalogEntity.class);
      query.setParameter("filter", filter);
      ArrayList<AlarmCatalogEntity> results = (ArrayList<AlarmCatalogEntity>) query.setMaxResults(
         Constant.MAXIME_RESULT_ALARM).getResultList();

      return results;
   }

}
