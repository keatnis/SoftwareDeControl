/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.HistorialTrabajador;
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
public class HistorialTrabajadorJpaController implements Serializable {

    public HistorialTrabajadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialTrabajador historialTrabajador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historialTrabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialTrabajador historialTrabajador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historialTrabajador = em.merge(historialTrabajador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historialTrabajador.getId();
                if (findHistorialTrabajador(id) == null) {
                    throw new NonexistentEntityException("The historialTrabajador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialTrabajador historialTrabajador;
            try {
                historialTrabajador = em.getReference(HistorialTrabajador.class, id);
                historialTrabajador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialTrabajador with id " + id + " no longer exists.", enfe);
            }
            em.remove(historialTrabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialTrabajador> findHistorialTrabajadorEntities() {
        return findHistorialTrabajadorEntities(true, -1, -1);
    }

    public List<HistorialTrabajador> findHistorialTrabajadorEntities(int maxResults, int firstResult) {
        return findHistorialTrabajadorEntities(false, maxResults, firstResult);
    }

    private List<HistorialTrabajador> findHistorialTrabajadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialTrabajador.class));
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

    public HistorialTrabajador findHistorialTrabajador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialTrabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialTrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialTrabajador> rt = cq.from(HistorialTrabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
