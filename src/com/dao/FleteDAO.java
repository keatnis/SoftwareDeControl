package com.dao;

import com.controller.AsignacionUnidadJpaController;
import com.controller.FleteJpaController;
import com.controller.RecargaCombustibleJpaController;
import com.model.AsignacionUnidad;
import com.model.RecargaCombustible;
import com.model.Flete;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class FleteDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private AsignacionUnidad asignacionUnidad;
    private Flete flete;
    private RecargaCombustible recargaCombustible;

    private AsignacionUnidadJpaController asignacionUnidadJpaController;
    private FleteJpaController fleteJpaController;
    private RecargaCombustibleJpaController recargaCombustibleJpaController;

    public FleteDAO() {
        this.asignacionUnidadJpaController = new AsignacionUnidadJpaController(emf);
        this.fleteJpaController = new FleteJpaController(emf);
        this.recargaCombustibleJpaController = new RecargaCombustibleJpaController(emf);
    }

    public void saveFlete(Flete flete, RecargaCombustible recargaCombustible, AsignacionUnidad asignacionUnidad) {

        try {

            flete.setLugarTrabajo(flete.getLugarTrabajo());

            asignacionUnidadJpaController.create(asignacionUnidad);

            flete.setAsignacionUnidad(asignacionUnidad);
            // recargaCombustible.setAsignacionUnidad(asignacionUnidad);

            recargaCombustibleJpaController.create(recargaCombustible);
            flete.setRecargaCombustible(recargaCombustible);
            fleteJpaController.create(flete);
            // System.out.println("lugar trabaho s" + asignacionUnidad.getOperador().getId());
            JOptionPane.showMessageDialog(null, "Datos del flete registrado correctamente");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public List<Flete> getDataFlete() {
        return fleteJpaController.findFleteEntities();
    }

    public List<Flete> getDataFleteOrderByDate() {
        return fleteJpaController.getFleteOrderByStatus();
    }

    public void updateStatus(Flete flete) {
        try {
            fleteJpaController.edit(flete);
            asignacionUnidadJpaController.edit(flete.getAsignacionUnidad());
            JOptionPane.showMessageDialog(null, "Estatus Actualizado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
