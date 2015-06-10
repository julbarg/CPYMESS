package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cpymes.entity.pymes.NitOnixEntity;


@Repository
public class NitOnixDAOImpl extends TemplateCPYMESDAO<NitOnixEntity> implements NitOnixDAO {

   @Override
   public ArrayList<NitOnixEntity> findByEstado(String estado) throws Exception {
      TypedQuery<NitOnixEntity> query = entityManager.createNamedQuery("NitOnixEntity.findByEstado",
         NitOnixEntity.class);
      query.setParameter("estado", estado);
      ArrayList<NitOnixEntity> results = (ArrayList<NitOnixEntity>) query.getResultList();

      return results;

   }

   @Override
   @Transactional(MANAGER_CPYMES)
   public void removeAll() throws Exception {
      Query query = entityManager.createQuery("DELETE FROM NitOnixEntity");
      query.executeUpdate();

   }

   @Override
   @Transactional(MANAGER_CPYMES)
   public void createList(ArrayList<NitOnixEntity> listNitOnix) throws Exception {
      for (NitOnixEntity nitOnix : listNitOnix) {
         entityManager.persist(nitOnix);
      }
   }

   public int findAllCount() throws Exception {
      int count = ((Number) entityManager.createNamedQuery("NitOnixEntity.findAllCount").getSingleResult()).intValue();

      return count;
   }

}
