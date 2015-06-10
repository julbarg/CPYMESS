package com.claro.cpymes.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cpymes.entity.pymes.ParametroEntity;


@Repository
public class ParameterDAOImpl extends TemplateCPYMESDAO<ParametroEntity> implements ParameterDAO {

   public String findByName(String name) throws Exception {
      Query query = entityManager.createNamedQuery("ParametroEntity.findByName");
      query.setParameter("name", name);
      String value = (String) query.getSingleResult();

      return value;
   }

   @Override
   @Transactional(MANAGER_CPYMES)
   public void updateParameter(String parameter, String value) throws Exception {
      ParametroEntity parametro = findById(parameter);
      parametro.setValue(value);
      update(parametro);
   }

   private ParametroEntity findById(String id) throws Exception {
      ParametroEntity parametro;
      parametro = entityManager.find(ParametroEntity.class, id);

      return parametro;
   }
}
