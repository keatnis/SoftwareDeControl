package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.Nomina;
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
public class NominaJpaController implements Serializable {

    public NominaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nomina nomina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomina);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Nomina registrado correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
 public void createAll(Nomina nomina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomina);
            em.getTransaction().commit();
          
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void edit(Nomina nomina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomina = em.merge(nomina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nomina.getId();
                if (findNomina(id) == null) {
                    throw new NonexistentEntityException("The nomina with id " + id + " no longer exists.");
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
            Nomina nomina;
            try {
                nomina = em.getReference(Nomina.class, id);
                nomina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomina with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nomina> findNominaEntities() {
        return findNominaEntities(true, -1, -1);
    }

    public List<Nomina> findNominaEntities(int maxResults, int firstResult) {
        return findNominaEntities(false, maxResults, firstResult);
    }

    private List<Nomina> findNominaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nomina.class));
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

    public Nomina findNomina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nomina.class, id);
        } finally {
            em.close();
        }
    }

    public int getNominaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nomina> rt = cq.from(Nomina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean existsNomina(Integer id) {
        String query = "select count(id) from NOMINA  where id=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);
    }

    public Object getNominaById(Integer id) {
        EntityManager em = getEntityManager();
        String sql = "SELECT fecha_fin FROM NOMINA WHERE operador_id=? AND fecha_fin IS NOT NULL ORDER BY fecha_pago ASC limit 1;";
        try {

            Object list = em.createNativeQuery(sql)
                    .setParameter(1, id).getSingleResult();
            return list;
        } catch (Exception e) {

            em.close();
            return null;
        }

    }

    public Double getTotalPrestamosBetweenDates(Integer idOperador, String startDate, String endDate) {
        final EntityManager em = getEntityManager();
        String query = "SELECT SUM(prestamo) FROM PRESTAMO p WHERE  p.fecha_prestamo BETWEEN '" + startDate + "' AND  '" + endDate + "' AND p.operador_id='" + idOperador + "'";

        // you will always get a single result
        Double count = (Double) em.createNativeQuery(query).getSingleResult();
        return count;
    }
}
