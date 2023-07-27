# ControlSystemRP
Software para el control de pago de operadores y control de mantenimiento, rendimiento de maquinaria y camiones.

# Requerimientos
  * Netbeans IDEA 
  * Java JDK 17
# Notas:
* El nombre de base de datos es controlSystem, si cambia el nombre debe de cambiar el nombre de la base de datos en el archivo src/META-INF/persistence.xml
* El archivo para iniciar la app es src/com/view/Login.java
* Importe la base de datos /lib/ControlSystem.sql

# Vista previ
# Login
Usuario: admin
Contraseña: admin
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/a08026e6-6ce9-4086-9ada-e657a36e8cdb)

#Menu principal * falta los demas modulos, pero ya etan las clases dao y model 
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/fd322230-1da8-4a1e-b7dc-b2c7ea852617)

# Modulo operador
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/a2f3692a-3355-427e-a0c5-be2f61e6f5aa)
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/2d42c126-52bb-4f30-978b-bdcfb9bef839)
visualizacion de doc pdf (dar click dos veces al boton)
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/6d43261d-1f73-441c-8e4b-c34b7fd9ecad)

# Modulo User
* la contaseña es encriptada al insertarse en la base de datos
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/507dd50e-963e-4932-9a60-c0549b2a3ba9)

# Modulo vehiculo
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/fc34230c-f58b-4ba0-b4bf-4561b5b2e1c3)
Registro de vehiculo
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/a7a8de5e-5046-4470-bbac-77004e7a3ac9)


# Modulo de servicios
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/600bca7d-406f-4182-bea8-ab8dc0f363c9)
Registrar servicio, dar clic en Nuevo
Para registrar el servicio primero se busca el vehiculo  por medio de la marca, modulo o num. de serie
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/01dfcda4-8350-4b3b-ab86-a4d832eb9fc2)


# Menu historial de servicios
Al buscar el servicio registrado por medio de la marca, modulo o nu de serie del vehiculo se puede obtener el historial de todos los servicios registrados de este vehiculo.
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/a3f15997-c466-4414-8c04-5bed8ff9b281)

# Funcion exportar datos a Excel
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/6b682206-bdb3-4dbf-8b93-00d89eca31e5)

![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/a24701d1-c8e3-4c15-947b-33aed2a253f4)
Archivo Excel
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/0351b009-609f-4a5e-8d30-ac39403245b9)
#Modulo flete
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/e06bad0b-5473-428f-8fa6-384e4edd41ac)
Registro flete
![image](https://github.com/keatnis/ControlSystemRP/assets/95552515/ca307c77-01b6-4ded-afcf-90154e9f555a)




