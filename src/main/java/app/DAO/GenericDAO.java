package app.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAO<T> {
  private EntityManager entityManager;
  private Class<T> type;

  public GenericDAO() {
    // TODO Auto-generated constructor stub
  }

  public GenericDAO(Class<T> type) {
    // TODO Auto-generated constructor stub

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
    // TODO Auto-generated method stub
    entityManager.persist(entity);
    entityManager.flush();
    return entity;
  }

  public Boolean delete(T entity) {
    // TODO Auto-generated method stub
    try {
      entityManager.remove(entity);
    } catch (Exception ex) {
      return false;
    }
    return true;
  }

  public T edit(T entity) {
    // TODO Auto-generated method stub
    try{
      return entityManager.merge(entity);
    } catch(Exception ex) {
      return null;
    }
  }

  public T find(Long entityId) {
    // TODO Auto-generated method stub
    return (T) entityManager.find(type, entityId);
  }
}
