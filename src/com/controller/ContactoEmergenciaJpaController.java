package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.ContactoEmergencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author keatnis
 */
public class ContactoEmergenciaJpaController implements Serializable {

    public ContactoEmergenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ContactoEmergenciaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ControlSystemPU");;
    }

    public void create(ContactoEmergencia contactoEmergencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contactoEmergencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContactoEmergencia contactoEmergencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contactoEmergencia = em.merge(contactoEmergencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contactoEmergencia.getId();
                if (findContactoEmergencia(id) == null) {
                    throw new NonexistentEntityException("The contactoEmergencia with id " + id + " no longer exists.");
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
            ContactoEmergencia contactoEmergencia;
            try {
                contactoEmergencia = em.getReference(ContactoEmergencia.class, id);
                contactoEmergencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactoEmergencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(contactoEmergencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContactoEmergencia> findContactoEmergenciaEntities() {
        return findContactoEmergenciaEntities(true, -1, -1);
    }

    public List<ContactoEmergencia> findContactoEmergenciaEntities(int maxResults, int firstResult) {
        return findContactoEmergenciaEntities(false, maxResults, firstResult);
    }

    private List<ContactoEmergencia> findContactoEmergenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContactoEmergencia.class));
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

    public ContactoEmergencia findContactoEmergencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContactoEmergencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoEmergenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContactoEmergencia> rt = cq.from(ContactoEmergencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
