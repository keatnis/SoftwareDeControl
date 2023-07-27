package com.model;

import com.model.Servicio;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Vehiculo.class)
public class Vehiculo_ { 

    public static volatile SingularAttribute<Vehiculo, String> descripcion;
    public static volatile ListAttribute<Vehiculo, Servicio> servicios;
    public static volatile SingularAttribute<Vehiculo, String> marca;
    public static volatile SingularAttribute<Vehiculo, String> tipoCombustible;
    public static volatile SingularAttribute<Vehiculo, Date> finRenta;
    public static volatile SingularAttribute<Vehiculo, Float> kmActual;
    public static volatile SingularAttribute<Vehiculo, Integer> id;
    public static volatile SingularAttribute<Vehiculo, String> type;
    public static volatile SingularAttribute<Vehiculo, String> modelo;
    public static volatile SingularAttribute<Vehiculo, String> numSerie;
    public static volatile SingularAttribute<Vehiculo, Float> capacidad;
    public static volatile SingularAttribute<Vehiculo, String> status;

}