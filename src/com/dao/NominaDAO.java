package com.dao;

import com.controller.NominaJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Nomina;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class NominaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private NominaJpaController nominaJpaController = new NominaJpaController(emf);

    public NominaDAO() {

    }

    public void save(Nomina nomina) {
        nominaJpaController.create(nomina);
    }

    public void saveAll(Nomina nomina) {
        nominaJpaController.createAll(nomina);
    }

    public void delete(Integer id) {
        try {
            nominaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void update(Nomina nomina) {
        if (nominaJpaController.existsNomina(nomina.getId())) {
            try {
                nominaJpaController.edit(nomina);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

    }

    public Object getLastPayDate(int id) {
        return nominaJpaController.getNominaById(id);
    }

    public Double getTotalPrestanos(Integer id, String startDate, String endDate) {
        return nominaJpaController.getTotalPrestamosBetweenDates(id, startDate, endDate);
    }
}
