package com.dao;

import com.controller.OperadorJpaController;
import com.controller.ServicioJpaController;
import com.model.Flete;
import com.model.Servicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author keatnis
 */
public class DashboardDAO {
    private FleteDAO fleteDAO;
    private OperadorJpaController operadorJpaController;
    private ServicioJpaController servicioJpaController;
    public DashboardDAO() {
        this.fleteDAO = new FleteDAO();
        this.operadorJpaController = new OperadorJpaController();
        this.servicioJpaController= new ServicioJpaController();
    }
    
     public List<Flete>  getDataFleteOrderByDate() {
        return fleteDAO.getDataFleteOrderByDate();
    }
    public List<Servicio> getServiceByMonth(){
        return servicioJpaController.getServicesByMonth();
    }
    public Object[] getCountDashboard(){
        return operadorJpaController.getCountDashboard();
    }
}
