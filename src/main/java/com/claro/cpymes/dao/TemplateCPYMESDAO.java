package com.claro.cpymes.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


/**
 * TemplateDAO - Define los metodos genericos en entidades
 * de la base de datos CPyMES
 * @author jbarragan
 *
 * @param <T>
 */
public class TemplateCPYMESDAO<T> {

   public static final String MANAGER_CPYMES = "transactionManagerCPYMES";

   private Class<T> clase;

   @PersistenceContext(unitName = "CPYMES")
   public EntityManager entityManager;

   public final void setClase(Class<T> entity) {
      this.clase = entity;
   }

   public T findOne(long id) {
      T t = entityManager.find(this.clase, id);

      return t;
   }

   @Transactional(MANAGER_CPYMES)
   public void create(T entity) {
      entityManager.persist(entity);
   }

   @Transactional(MANAGER_CPYMES)
   public T update(T entity) {
      T t = entityManager.merge(entity);

      return t;
   }

   @Transactional(MANAGER_CPYMES)
   public void delete(T entity) {
      entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

   }

   @Transactional(MANAGER_CPYMES)
   public void deleteById(long entityId) {
      T entity = this.findOne(entityId);
      this.delete(entity);
   }

}
