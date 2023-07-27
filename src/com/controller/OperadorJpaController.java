package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.dao.DashboardCountDTO;
import com.model.Operador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import org.eclipse.persistence.sessions.remote.corba.sun._CORBARemoteSessionControllerImplBase;

/**
 *
 * @author keatnis
 */
public class OperadorJpaController implements Serializable {

    public OperadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public OperadorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operador operador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(operador);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null,
                    "Trabajador registrado correctamente", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operador operador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            operador = em.merge(operador);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Datos actualizados corectamente", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operador.getId();
                if (findOperador(id) == null) {
                    throw new NonexistentEntityException("The operador with id " + id + " no longer exists.");
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
            Operador operador;
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                operador = em.getReference(Operador.class, id);
                operador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operador with id " + id + " no longer exists.", enfe);
            }
            em.remove(operador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operador> findOperadorEntities() {
        return findOperadorEntities(true, -1, -1);
    }

    public List<Operador> findOperadorEntities(int maxResults, int firstResult) {
        return findOperadorEntities(false, maxResults, firstResult);
    }

    private List<Operador> findOperadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operador.class));
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

    public Operador findOperador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operador.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operador> rt = cq.from(Operador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Operador> getOperadorByNameLastName(String key) {
        String sqlString = "SELECT * FROM OPERADOR  WHERE nombre LIKE '%" + key + "%' ";
        EntityManager em = getEntityManager();
        List<Operador> list = em.createNativeQuery(sqlString, Operador.class)
                .getResultList();
        return list;

    }

    public boolean existOperador(Integer id) {
        String query = "select count(operador_id) from OPERADOR  where operador_id=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);
    }

    public Object[] getCountDashboard() {
        EntityManager em = getEntityManager();
        String query = "SELECT count(id) AS combustible,\n"
                + "(SELECT count(vehiculo_id) from VEHICULO ) as vehiculos,\n"
                + "(select count(operador_id) from OPERADOR ) as trabajadores,\n"
                + "(SELECT count(id) from FLETE) AS fletes\n"
                + "FROM RECARGA_COMBUSTIBLE;";
        // you will always get a single result

        return (Object[]) em.createNativeQuery(query).getSingleResult();
    }

    public List<Operador> getAllOperador() {
        EntityManager em = getEntityManager();
        List<Operador> operadors = em.createNativeQuery("Operador.findAllConcat", Operador.class).getResultList();
        return operadors;
    }

}
