package com.model;

import com.model.AsignacionUnidad;
import com.model.RecargaCombustible;
import com.model.Workplace;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Flete.class)
public class Flete_ { 

    public static volatile SingularAttribute<Flete, Date> fecha;
    public static volatile SingularAttribute<Flete, String> responsable;
    public static volatile SingularAttribute<Flete, String> concepto;
    public static volatile SingularAttribute<Flete, RecargaCombustible> recargaCombustible;
    public static volatile SingularAttribute<Flete, String> recibe;
    public static volatile SingularAttribute<Flete, AsignacionUnidad> asignacionUnidad;
    public static volatile SingularAttribute<Flete, Integer> id;
    public static volatile SingularAttribute<Flete, Workplace> workplace;
    public static volatile SingularAttribute<Flete, String> lugarSalida;
    public static volatile SingularAttribute<Flete, String> status;

}