package com.dao;

import com.controller.WorkplaceJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Workplace;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class LugarTrabajoDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private final WorkplaceJpaController workplaceJpaController;

    public LugarTrabajoDAO() {
        this.workplaceJpaController = new WorkplaceJpaController(emf);
    }

    public void save(Workplace workplace) {
        workplaceJpaController.create(workplace);
    }

    public void update(Workplace workplace) {
        if (workplaceJpaController.workplaceExist(workplace.getId())) {
            try {
                workplaceJpaController.edit(workplace);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            return;
        }

    }

    public void delete(Integer idWorkplace) {

        try {
            workplaceJpaController.destroy(idWorkplace);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    public List<Workplace> getWorkplaces(){
        return workplaceJpaController.findWorkplaceEntities();
    }
}
