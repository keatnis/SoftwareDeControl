package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.AsignacionUnidad;
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
public class AsignacionUnidadJpaController implements Serializable {

    public AsignacionUnidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsignacionUnidad asignacionUnidad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asignacionUnidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsignacionUnidad asignacionUnidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asignacionUnidad = em.merge(asignacionUnidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignacionUnidad.getId();
                if (findAsignacionUnidad(id) == null) {
                    throw new NonexistentEntityException("The asignacionUnidad with id " + id + " no longer exists.");
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
            AsignacionUnidad asignacionUnidad;
            try {
                asignacionUnidad = em.getReference(AsignacionUnidad.class, id);
                asignacionUnidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignacionUnidad with id " + id + " no longer exists.", enfe);
            }
            em.remove(asignacionUnidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsignacionUnidad> findAsignacionUnidadEntities() {
        return findAsignacionUnidadEntities(true, -1, -1);
    }

    public List<AsignacionUnidad> findAsignacionUnidadEntities(int maxResults, int firstResult) {
        return findAsignacionUnidadEntities(false, maxResults, firstResult);
    }

    private List<AsignacionUnidad> findAsignacionUnidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsignacionUnidad.class));
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

    public AsignacionUnidad findAsignacionUnidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsignacionUnidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignacionUnidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsignacionUnidad> rt = cq.from(AsignacionUnidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
