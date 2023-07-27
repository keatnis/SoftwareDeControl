package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.model.Vehiculo;
import java.io.Serializable;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vehiculo);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(
                    null, "Vehiculo registrado correctamente");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculo vehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vehiculo = em.merge(vehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehiculo.getId();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
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
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
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

    public Vehiculo findVehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean vehiculoExist(Integer id) {
        String query = "select count(vehiculo_id) from VEHICULO  where vehiculo_id=" + id;
        final EntityManager em = getEntityManager();
        // you will always get a single result
        Long count = (Long) em.createNativeQuery(query).getSingleResult();
        return ((count.equals(0L)) ? false : true);

    }

    public List<Vehiculo> getVehiculoByMMN(String key) {
        String sqlString = "SELECT * FROM VEHICULO as v WHERE v.marca like '%" + key + "%' "
                + "OR v.modelo LIKE '%" + key + "%' OR v.num_serie LIKE '%" + key + "%'";
        EntityManager em = getEntityManager();
        List<Vehiculo> list = em.createNativeQuery(sqlString, Vehiculo.class)
                .setParameter("marca", key).getResultList();
        return list;

    }

    /*
    Actualizar km al la db
     */
    public void updateKMByIdVehiculo(Integer idVehiculo, Float km) {
        String query = "UPDATE VEHICULO SET km_actual ='" + km + "' WHERE vehiculo_id =" + idVehiculo + "";
    
        EntityManager em = emf.createEntityManager();
        try {
            EntityTransaction entr = em.getTransaction();
            entr.begin();
            var query2 = em.createNativeQuery(query);
          
            int updateKM = query2.executeUpdate();
            System.out.println(updateKM + " km updated");
            entr.commit();
        } finally {
            em.close();
        }

    }

}
