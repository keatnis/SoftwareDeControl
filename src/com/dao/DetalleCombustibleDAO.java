package com.dao;

import com.controller.DetalleCombustibleJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.DetalleCombustible;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class DetalleCombustibleDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private final DetalleCombustibleJpaController combustibleJpaController;

    public DetalleCombustibleDAO() {
        this.combustibleJpaController = new DetalleCombustibleJpaController(emf);
    }

    public void save(DetalleCombustible detalleCombustible) {
        combustibleJpaController.create(detalleCombustible);
    }

    public void edit(DetalleCombustible detalleCombustible) {
        if (combustibleJpaController.detalleCombustibleExists((detalleCombustible.getId()))) {
            try {
                combustibleJpaController.edit(detalleCombustible);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void delete(Integer idDetalle) {
        try {
            combustibleJpaController.destroy(idDetalle);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public List<DetalleCombustible> getAllDetalles() {
        return combustibleJpaController.findDetalleCombustibleEntities();
    }
}
