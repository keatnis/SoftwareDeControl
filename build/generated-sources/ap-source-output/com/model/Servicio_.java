package com.model;

import com.model.Vehiculo;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Servicio.class)
public class Servicio_ { 

    public static volatile SingularAttribute<Servicio, String> descripcion;
    public static volatile SingularAttribute<Servicio, String> metodoPago;
    public static volatile SingularAttribute<Servicio, Date> fecha;
    public static volatile SingularAttribute<Servicio, Float> km;
    public static volatile SingularAttribute<Servicio, Float> precio;
    public static volatile SingularAttribute<Servicio, Date> proximoServicio;
    public static volatile SingularAttribute<Servicio, String> observaciones;
    public static volatile SingularAttribute<Servicio, Vehiculo> vehiculo;
    public static volatile SingularAttribute<Servicio, Integer> id;
    public static volatile SingularAttribute<Servicio, Float> cantidad;
    public static volatile SingularAttribute<Servicio, String> empresa;
    public static volatile SingularAttribute<Servicio, Float> importe;

}