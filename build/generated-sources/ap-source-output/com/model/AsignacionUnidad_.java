package com.model;

import com.model.Operador;
import com.model.Vehiculo;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(AsignacionUnidad.class)
public class AsignacionUnidad_ { 

    public static volatile SingularAttribute<AsignacionUnidad, Float> kmInicio;
    public static volatile SingularAttribute<AsignacionUnidad, Float> kmFinal;
    public static volatile SingularAttribute<AsignacionUnidad, Date> fechaInicio;
    public static volatile SingularAttribute<AsignacionUnidad, Integer> id;
    public static volatile SingularAttribute<AsignacionUnidad, Vehiculo> vehiculo;
    public static volatile SingularAttribute<AsignacionUnidad, Date> fechaFin;
    public static volatile SingularAttribute<AsignacionUnidad, Operador> operador;
    public static volatile SingularAttribute<AsignacionUnidad, String> status;

}