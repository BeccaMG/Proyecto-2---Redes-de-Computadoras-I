/**
 * @file AuthServices.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Interfaz del objeto remoto correspondiente a los servicios de
 * autenticación.
 */

import java.util.*;
import java.io.*;
import java.rmi.*;

public interface AuthServices extends Remote {

   /**
    * autenticado
    * 
    * @brief Verifica si un usuario está correctamente autenticado.
    * 
    * @param usuario Nombre del usuario.
    * @param password Contraseña del usuario.
    * 
    * @return true si el usuario existe en la base de datos y si su contraseña
    * coincide, false en caso contrario.
    */
	public boolean autenticado(String usuario, String password) 
        throws RemoteException;
        
}