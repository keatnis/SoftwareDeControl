package com.dao;

import com.controller.JobJpaController;
import com.controller.OperadorJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.model.Operador;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author keatnis
 */
public class TrabajadoresDAO implements Trabajadores {

    private final OperadorJpaController operadorController;
    private JobJpaController jobJpaController;
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");

    public TrabajadoresDAO() {
        this.operadorController = new OperadorJpaController();
        this.jobJpaController = new JobJpaController();
    }

    @Override
    public List<Operador> operadores() {

        return operadorController.findOperadorEntities();
    }

    public List<Operador> getAllJoin() {

        return operadorController.getAllOperador();

    }

    @Override
    public void save(Operador operador) {

        operador.setContactoEmergencia(operador.getContactoEmergencia());

     //   jobJpaController.create(operador.getJob());
      //  operador.setJob(operador.getJob());
        operadorController.create(operador);

    }

    @Override
    public void edit(Operador operador) {
        if (operadorController.existOperador(operador.getId())) {
            try {
                operadorController.edit(operador);
            } catch (Exception ex) {
                System.out.println("message:" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "");
        }
    }

    @Override
    public void delete(Integer id) {
        if (operadorController.existOperador(id)) {
            try {
                operadorController.destroy(id);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    @Override
    public List<Operador> searchOperador(String key) {
        return operadorController.getOperadorByNameLastName(key);
    }

}
