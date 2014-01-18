/**
 * @file AuthServicesImpl.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Objeto remoto correspondiente a los servicios de
 * autenticación implementando la interfaz AuthServices.
 */

import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class AuthServicesImpl 
	extends UnicastRemoteObject implements AuthServices {
	
	private Map <String, String> dicUserPass;

   /**
    * AuthServicesImpl
    * 
    * @brief Constructor.
    *  
    * @throws RemoteException Error en caso de invocación de método remoto.
    */
	public AuthServicesImpl() throws RemoteException {
		super();
        this.dicUserPass = new HashMap <String,String> ();
	}

   /**
    * setDiccionario
    * 
    * @brief Fija el la base de datos de autenticación.
    * 
    * @param diccionario Base de datos.
    */ 
	public void setDiccionario(Map <String, String> diccionario) {
		this.dicUserPass.putAll(diccionario);
	}

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
    * 
    * @throws RemoteException Error en caso de invocación de método remoto.
    */ 
	public boolean autenticado(String usuario, String password) 
        throws RemoteException {
		
		if(dicUserPass.containsKey(usuario) && 
		   dicUserPass.get(usuario).equals(password)) {
			return true;
		}
		
		return false;
	}
}