package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.Flete;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class FleteJpaController implements Serializable {

    public FleteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Flete flete) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(flete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Flete flete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            flete = em.merge(flete);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Flete actualizado correctamente");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = flete.getId();
                if (findFlete(id) == null) {
                    throw new NonexistentEntityException("The flete with id " + id + " no longer exists.");
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
            Flete flete;
            try {
                flete = em.getReference(Flete.class, id);
                flete.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The flete with id " + id + " no longer exists.", enfe);
            }
            em.remove(flete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Flete> findFleteEntities() {
        return findFleteEntities(true, -1, -1);
    }

    public List<Flete> findFleteEntities(int maxResults, int firstResult) {
        return findFleteEntities(false, maxResults, firstResult);
    }

    private List<Flete> findFleteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Flete.class));
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

    public Flete findFlete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Flete.class, id);
        } finally {
            em.close();
        }
    }

    public int getFleteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Flete> rt = cq.from(Flete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Flete> getFleteOrderByStatus() {
        EntityManager em = getEntityManager();
        try {

            List<Flete> list = em.createNamedQuery("Flete.findByStatus", Flete.class)
                    .setParameter("status", "Activo").getResultList();
            return list;
        } catch (Exception e) {

            em.close();
            return null;
        }

    }
}
