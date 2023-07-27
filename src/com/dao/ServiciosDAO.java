package com.dao;

import com.controller.ServicioJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Servicio;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class ServiciosDAO {

    private ServicioJpaController servicioJpaController;

    public ServiciosDAO() {
   
      this.servicioJpaController= new ServicioJpaController();
    }

    public void save(Servicio servicio) {
        servicioJpaController.create(servicio);
    }

    public List<Servicio> getAllServiceByEntities() {

        return servicioJpaController.findServicioEntities();
    }

    public List<Servicio> getAllServicesByDate() {
        return servicioJpaController.getServicesOrderByDate();
    }

    public List<Servicio> getServiciosById(Integer id) {
        return servicioJpaController.getServicesById(id);
    }

    public void update(Servicio servicio) {
        if (servicioJpaController.servicioExits(servicio.getId())) {
            try {
                servicioJpaController.edit(servicio);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

    }

    public void delete(Integer id) {
        if (servicioJpaController.servicioExits(id)) {
            try {
                servicioJpaController.destroy(id);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
//    public void updateKM(Integer idVehiculo,Float newKM){
//        servicioJpaController.updateKMByIdVehiculo(idVehiculo, newKM);
//    }
}
