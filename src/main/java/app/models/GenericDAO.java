package app.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GenericDAO<T> {
  private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernatepu");
  private EntityManager entityManager = entityManagerFactory.createEntityManager();
  private Class<T> type;

  public GenericDAO() {
  }

  public GenericDAO(Class<T> type) {
    this.type = type;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public T save(T entity) {
    EntityManager em = entityManager;
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
    return entity;
  }

  public Boolean delete(T entity) {
    EntityManager em = entityManager;
    try {
      em.getTransaction().begin();
      entityManager.remove(entity);
      em.getTransaction().commit();
    } catch (Exception ex) {
      return false;
    }
    return true;
  }

  public T edit(T entity) {
    EntityManager em = entityManager;
    try{
      em.getTransaction().begin();
      T res = entityManager.merge(entity);
      em.getTransaction().commit();
      return res;
    } catch(Exception ex) {
      return null;
    }
  }

  public T find(Long entityId) {
    return (T) entityManager.find(type, entityId);
  }

  public List<T> findAll(){
    return (List<T>) entityManager.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
  }

  public void saveAll(List<T> entities){
    EntityManager em = entityManager;
      em.getTransaction().begin();
    for (T entity : entities) {
      em.persist(entity);
    }
      em.getTransaction().commit();
  }
}
