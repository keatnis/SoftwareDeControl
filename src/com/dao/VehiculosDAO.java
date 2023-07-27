package com.dao;

import com.controller.VehiculoJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Vehiculo;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class VehiculosDAO {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private final VehiculoJpaController vehiculosJpaController;
    
    public VehiculosDAO() {
        this.vehiculosJpaController = new VehiculoJpaController(emf);
    }
    
    public void addVehiculo(Vehiculo vehiculo) {
        vehiculosJpaController.create(vehiculo);
        
    }
    
    public List<Vehiculo> getAllVehiculos() {
        List<Vehiculo> list = vehiculosJpaController.findVehiculoEntities();
        return list;
    }
    public List<Vehiculo> getByKey(String key){
       return vehiculosJpaController.getVehiculoByMMN(key);
    }
    public void update(Vehiculo vehiculo) {
        if (vehiculosJpaController.vehiculoExist(vehiculo.getId())) {
            try {
                vehiculosJpaController.edit(vehiculo);
            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(null, ex.getMessage());
            }
        }
        
    }
    
    public void delete(Integer id) {
        if (vehiculosJpaController.vehiculoExist(id)) {
            try {
                vehiculosJpaController.destroy(id);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showConfirmDialog(null, ex.getMessage());
            }            
        }
        
    }
        public void updateKM(Integer idVehiculo,Float newKM){
        vehiculosJpaController.updateKMByIdVehiculo(idVehiculo, newKM);
    }
}
