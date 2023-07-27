package com.dao;

import com.controller.NominaJpaController;
import com.model.Nomina;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author keatnis
 */
public class NominaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControlSystemPU");
    private NominaJpaController nominaJpaController = new NominaJpaController(emf);

    public NominaDAO() {

    }
    
    public   List<Nomina>getLastPayDate(int id){
      return  nominaJpaController.getNominaById(id);
    }
    
    
}
