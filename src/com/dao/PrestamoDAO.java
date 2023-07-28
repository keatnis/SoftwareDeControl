package com.dao;

import com.controller.PrestamoJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Prestamo;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class PrestamoDAO {

    private PrestamoJpaController prestamoJpaController;

    public PrestamoDAO() {
        this.prestamoJpaController = new PrestamoJpaController();
    }

    public void save(Prestamo prestamo) {
        prestamoJpaController.create(prestamo);
    }

    public void update(Prestamo prestamo) {
        if (prestamoJpaController.existRegPrestamo(prestamo.getId())) {
            try {
                prestamoJpaController.edit(prestamo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void delete(int idPrestamo) {
        if (prestamoJpaController.existRegPrestamo(idPrestamo)) {
            try {
                prestamoJpaController.destroy(idPrestamo);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

    }

    public List<Prestamo> getPrestamoById(int idOperador) {
        return prestamoJpaController.findByIdOperador(idOperador);
    }

    public List<Prestamo> getPrestamosBetweenDates(int idOperador, String startDate, String endDate) {
        return prestamoJpaController.findBetweenDates(idOperador, startDate, endDate);
    }
}
