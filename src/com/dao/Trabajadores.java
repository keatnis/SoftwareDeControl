package com.dao;

import com.model.ContactoEmergencia;
import com.model.Operador;
import java.util.List;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author keatnis
 */
public interface Trabajadores {
   List<Operador> operadores() ;
   void save(Operador operador);
   void edit(Operador operador);
   void delete(Integer id);
    List<Operador> searchOperador(String key);
}
