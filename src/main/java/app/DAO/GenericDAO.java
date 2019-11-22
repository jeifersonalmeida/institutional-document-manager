package app.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GenericDAO<T> {
  private EntityManager entityManager;
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
    entityManager.persist(entity);
    entityManager.flush();
    return entity;
  }

  public Boolean delete(T entity) {
    try {
      entityManager.remove(entity);
    } catch (Exception ex) {
      return false;
    }
    return true;
  }

  public T edit(T entity) {
    try{
      return entityManager.merge(entity);
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
}
