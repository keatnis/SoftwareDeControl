package com.model;

import com.model.Operador;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(HistorialTrabajador.class)
public class HistorialTrabajador_ { 

    public static volatile SingularAttribute<HistorialTrabajador, Date> fechaBaja;
    public static volatile SingularAttribute<HistorialTrabajador, Date> fechaInicio;
    public static volatile SingularAttribute<HistorialTrabajador, Long> id;
    public static volatile SingularAttribute<HistorialTrabajador, Operador> operador;

}