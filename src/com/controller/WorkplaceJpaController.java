package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.Workplace;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author keatnis
 */
public class WorkplaceJpaController implements Serializable {

    public WorkplaceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workplace workplace) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(workplace);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workplace workplace) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            workplace = em.merge(workplace);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = workplace.getId();
                if (findWorkplace(id) == null) {
                    throw new NonexistentEntityException("The workplace with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workplace workplace;
            try {
                workplace = em.getReference(Workplace.class, id);
                workplace.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workplace with id " + id + " no longer exists.", enfe);
            }
            em.remove(workplace);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Workplace> findWorkplaceEntities() {
        return findWorkplaceEntities(true, -1, -1);
    }

    public List<Workplace> findWorkplaceEntities(int maxResults, int firstResult) {
        return findWorkplaceEntities(false, maxResults, firstResult);
    }

    private List<Workplace> findWorkplaceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workplace.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Workplace findWorkplace(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workplace.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkplaceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workplace> rt = cq.from(Workplace.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
      public boolean workplaceExist(Integer id) {
        String query = "select count(id) from WORKPLACE  where id=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);

    }
    
}
