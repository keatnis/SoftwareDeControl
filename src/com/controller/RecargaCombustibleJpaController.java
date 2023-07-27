package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.RecargaCombustible;
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
public class RecargaCombustibleJpaController implements Serializable {

    public RecargaCombustibleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecargaCombustible recargaCombustible) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(recargaCombustible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecargaCombustible recargaCombustible) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            recargaCombustible = em.merge(recargaCombustible);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recargaCombustible.getId();
                if (findRecargaCombustible(id) == null) {
                    throw new NonexistentEntityException("The recargaCombustible with id " + id + " no longer exists.");
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
            RecargaCombustible recargaCombustible;
            try {
                recargaCombustible = em.getReference(RecargaCombustible.class, id);
                recargaCombustible.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recargaCombustible with id " + id + " no longer exists.", enfe);
            }
            em.remove(recargaCombustible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecargaCombustible> findRecargaCombustibleEntities() {
        return findRecargaCombustibleEntities(true, -1, -1);
    }

    public List<RecargaCombustible> findRecargaCombustibleEntities(int maxResults, int firstResult) {
        return findRecargaCombustibleEntities(false, maxResults, firstResult);
    }

    private List<RecargaCombustible> findRecargaCombustibleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecargaCombustible.class));
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

    public RecargaCombustible findRecargaCombustible(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecargaCombustible.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecargaCombustibleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecargaCombustible> rt = cq.from(RecargaCombustible.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
