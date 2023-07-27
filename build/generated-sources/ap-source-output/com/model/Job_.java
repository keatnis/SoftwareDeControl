package com.model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Job.class)
public class Job_ { 

    public static volatile SingularAttribute<Job, String> puesto;
    public static volatile SingularAttribute<Job, Float> sueldoDiario;
    public static volatile SingularAttribute<Job, boolean[]> diasLaborales;
    public static volatile SingularAttribute<Job, Long> id;
    public static volatile SingularAttribute<Job, Integer> totalDiasLaborales;

}