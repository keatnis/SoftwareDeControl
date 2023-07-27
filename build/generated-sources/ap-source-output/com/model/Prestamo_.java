package com.model;

import com.model.Operador;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Prestamo.class)
public class Prestamo_ { 

    public static volatile SingularAttribute<Prestamo, String> descripcion;
    public static volatile SingularAttribute<Prestamo, Float> prestamo;
    public static volatile SingularAttribute<Prestamo, Date> fehcaPrestamo;
    public static volatile SingularAttribute<Prestamo, Long> id;
    public static volatile SingularAttribute<Prestamo, Operador> operador;

}