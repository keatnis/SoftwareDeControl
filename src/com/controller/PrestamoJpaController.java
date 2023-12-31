package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.Prestamo;
import com.model.User;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class PrestamoJpaController implements Serializable {

    public PrestamoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prestamo prestamo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(prestamo);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Prestamo registrado");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prestamo prestamo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            prestamo = em.merge(prestamo);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Prestamo editado");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = prestamo.getId();
                if (findPrestamo(id) == null) {
                    throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prestamo prestamo;
            try {
                prestamo = em.getReference(Prestamo.class, id);
                prestamo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.", enfe);
            }
            em.remove(prestamo);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Prestamo eliminado");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prestamo> findPrestamoEntities() {
        return findPrestamoEntities(true, -1, -1);
    }

    public List<Prestamo> findPrestamoEntities(int maxResults, int firstResult) {
        return findPrestamoEntities(false, maxResults, firstResult);
    }

    private List<Prestamo> findPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prestamo.class));
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

    public Prestamo findPrestamo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prestamo> rt = cq.from(Prestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean existRegPrestamo(Integer id) {
        String query = "select count(ID) from PRESTAMO  where ID=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);
    }

    public List<Prestamo> findByIdOperador(int idOperador) {
        EntityManager em = getEntityManager();
        String query = "SELECT * from PRESTAMO p WHERE p.operador_id =: idOperador";
        List<Prestamo> userList = em.createNamedQuery(query)
                .setParameter("idOperador", idOperador).getResultList();

        return userList;
    }

    public List<Prestamo> findBetweenDates(int idOperador, String startDate, String endDate) {
        //System.out.println("datos enciados "+idOperador+' '+startDate+' '+endDate);
        EntityManager em = getEntityManager();
        String query = "SELECT * FROM PRESTAMO p WHERE  p.fecha_prestamo BETWEEN '"+startDate+"' AND  '"+endDate+"' AND p.operador_id='"+idOperador+"'";
        List<Prestamo> userList = em.createNativeQuery(query,Prestamo.class)
                        .getResultList();

        return userList;
    }
}
