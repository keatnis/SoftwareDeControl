package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.DetalleCombustible;
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
public class DetalleCombustibleJpaController implements Serializable {

    public DetalleCombustibleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCombustible detalleCombustible) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(detalleCombustible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCombustible detalleCombustible) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            detalleCombustible = em.merge(detalleCombustible);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCombustible.getId();
                if (findDetalleCombustible(id) == null) {
                    throw new NonexistentEntityException("The detalleCombustible with id " + id + " no longer exists.");
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
            DetalleCombustible detalleCombustible;
            try {
                detalleCombustible = em.getReference(DetalleCombustible.class, id);
                detalleCombustible.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCombustible with id " + id + " no longer exists.", enfe);
            }
            em.remove(detalleCombustible);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCombustible> findDetalleCombustibleEntities() {
        return findDetalleCombustibleEntities(true, -1, -1);
    }

    public List<DetalleCombustible> findDetalleCombustibleEntities(int maxResults, int firstResult) {
        return findDetalleCombustibleEntities(false, maxResults, firstResult);
    }

    private List<DetalleCombustible> findDetalleCombustibleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCombustible.class));
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

    public DetalleCombustible findDetalleCombustible(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCombustible.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCombustibleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCombustible> rt = cq.from(DetalleCombustible.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public boolean detalleCombustibleExists(Integer id) {
        String query = "select count(ID) from DETALLE_COMBUSTIBLE  where ID=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);

    }
}
