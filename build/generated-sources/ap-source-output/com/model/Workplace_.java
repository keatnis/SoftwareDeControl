package com.model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Workplace.class)
public class Workplace_ { 

    public static volatile SingularAttribute<Workplace, Date> fechaInicio;
    public static volatile SingularAttribute<Workplace, String> periodo;
    public static volatile SingularAttribute<Workplace, String> nombreTrabajo;
    public static volatile SingularAttribute<Workplace, Integer> id;
    public static volatile SingularAttribute<Workplace, String> claveTrabajo;
    public static volatile SingularAttribute<Workplace, Date> fechaFin;

}