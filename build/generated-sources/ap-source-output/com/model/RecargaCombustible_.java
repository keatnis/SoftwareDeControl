package com.model;

import com.model.DetalleCombustible;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-21T22:52:06", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(RecargaCombustible.class)
public class RecargaCombustible_ { 

    public static volatile SingularAttribute<RecargaCombustible, String> gasolinera;
    public static volatile SingularAttribute<RecargaCombustible, String> tipoCombustible;
    public static volatile SingularAttribute<RecargaCombustible, Float> monto;
    public static volatile SingularAttribute<RecargaCombustible, Float> odometroActual;
    public static volatile SingularAttribute<RecargaCombustible, Float> litros;
    public static volatile SingularAttribute<RecargaCombustible, Float> precioxlitro;
    public static volatile SingularAttribute<RecargaCombustible, String> tipoPago;
    public static volatile SingularAttribute<RecargaCombustible, Integer> id;
    public static volatile SingularAttribute<RecargaCombustible, DetalleCombustible> detalleCompustible;

}