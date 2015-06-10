package com.claro.cpymes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


public class TemplateIVRDAO<T> {

   public static final String MANAGER_IVR = "transactionManagerIVR";

   private Class<T> clase;

   @PersistenceContext(unitName = "IVR")
   public EntityManager entityManager;

   public final void setClase(Class<T> entity) {
      this.clase = entity;
   }

   public T findOne(long id) {
      T t = entityManager.find(this.clase, id);

      return t;
   }

   @Transactional(MANAGER_IVR)
   public void create(T entity) {
      entityManager.persist(entity);
   }

   @Transactional(MANAGER_IVR)
   public T update(T entity) {
      T t = entityManager.merge(entity);

      return t;
   }

   @Transactional(MANAGER_IVR)
   public void delete(T entity) {
      entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

   }

   @Transactional(MANAGER_IVR)
   public void deleteById(long entityId) {
      T entity = this.findOne(entityId);
      this.delete(entity);
   }

}
