package com.dao;

/**
 *
 * @author keatnis
 */
public class DashboardCountDTO {

    private int totalVehiculos;
    private int totalOperadores;
    private int totalRecargasCombustible;
    private int totalFletes;

    public DashboardCountDTO(int totalVehiculos, int totalOperadores, int totalRecargasCombustible, int totalFletes) {
        super();
        this.totalVehiculos = totalVehiculos;
        this.totalOperadores = totalOperadores;
        this.totalRecargasCombustible = totalRecargasCombustible;
        this.totalFletes = totalFletes;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public int getTotalOperadores() {
        return totalOperadores;
    }

    public void setTotalOperadores(int totalOperadores) {
        this.totalOperadores = totalOperadores;
    }

    public int getTotalRecargasCombustible() {
        return totalRecargasCombustible;
    }

    public void setTotalRecargasCombustible(int totalRecargasCombustible) {
        this.totalRecargasCombustible = totalRecargasCombustible;
    }

    public int getTotalFletes() {
        return totalFletes;
    }

    public void setTotalFletes(int totalFletes) {
        this.totalFletes = totalFletes;
    }
        
}
