package com.model;

import com.model.ContactoEmergencia;
import com.model.Job;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Operador.class)
public class Operador_ { 

    public static volatile SingularAttribute<Operador, String> estado;
    public static volatile SingularAttribute<Operador, String> calle;
    public static volatile SingularAttribute<Operador, String> num;
    public static volatile SingularAttribute<Operador, String> apeMaterno;
    public static volatile SingularAttribute<Operador, String> nombre;
    public static volatile SingularAttribute<Operador, String> colonia;
    public static volatile SingularAttribute<Operador, String> alergias;
    public static volatile SingularAttribute<Operador, byte[]> file;
    public static volatile SingularAttribute<Operador, ContactoEmergencia> contactoEmergencia;
    public static volatile SingularAttribute<Operador, String> ciudad;
    public static volatile SingularAttribute<Operador, String> telefono2;
    public static volatile SingularAttribute<Operador, Integer> id;
    public static volatile SingularAttribute<Operador, String> typeblood;
    public static volatile SingularAttribute<Operador, String> telefono;
    public static volatile SingularAttribute<Operador, Job> job;
    public static volatile SingularAttribute<Operador, String> apePaterno;
    public static volatile SingularAttribute<Operador, Boolean> status;

}