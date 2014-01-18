/**
 * @file FileServices.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Interfaz del objeto remoto correspondiente a los servicios de archivos
 * remotos.
 */

import java.util.*;
import java.io.*;
import java.rmi.*;

import com.healthmarketscience.rmiio.*;

public interface FileServices extends Remote {

   /**
    * archivosEnServidor
    * 
    * @brief Lista los archivos disponibles en el servidor.
    * 
    * @param usuario Nombre del usuario que ejecuta el comando 'rls'.
    * @param password Contraseña del usuario.
    * 
    * @return Una lista con los archivos disponibles en el servidor.
    * 
    * @throws RemoteException Error en caso de invocación de método remoto.
    * @throws NoAutenticadoException Error en caso de usuario no autenticado.
    */
    public ArrayList <Archivo> archivosEnServidor(
        String usuario, String clave)
            throws RemoteException, NoAutenticadoException;

   /**
    * subirArchivo
    * 
    * @brief Sube un archivo al servidor.
    * 
    * @param usuario Nombre del usuario que ejecuta el comando 'sub'.
    * @param password Contraseña del usuario.
    * @param archivo Nombre del archivo a subir.
    * @param fileDate Contenido del archivo.
    * 
    * @return true si el archivo fue subido exitosamente, false en caso de que
    * el archivo ya exista en el servidor.
    * 
    * @throws RemoteException Error en caso de invocación de método remoto.
    * @throws NoAutenticadoException Error en caso de usuario no autenticado.
    * @throws IOException Error en caso de abrir el archivo.
    * 
    * @see com.healthmarketscience.rmiio.RemoteInputStream.java
    */
	public boolean subirArchivo(
        String usuario,String clave,String archivo,RemoteInputStream fileData)
            throws RemoteException, IOException, NoAutenticadoException;
    
   /**
    * bajarArchivo
    * 
    * @brief Descarga un archivo del servidor.
    * 
    * @param usuario Nombre del usuario que ejecuta el comando 'baj'.
    * @param password Contraseña del usuario.
    * @param archivo Nombre del archivo a bajar.
    * 
    * @return El contenido del archivo seleccionado.
    * 
    * @throws RemoteException Error en caso de invocación de método remoto.
    * @throws NoAutenticadoException Error en caso de usuario no autenticado.
    * @throws IOException Error en caso de abrir el archivo.
    * 
    * @see com.healthmarketscience.rmiio.RemoteInputStream.java
    */
	public RemoteInputStream bajarArchivo(
        String usuario,String clave,String archivo) 
            throws RemoteException, IOException, NoAutenticadoException;
        
   /**
    * borrarArchivo
    * 
    * @brief Borra un archivo del servidor.
    * 
    * @param usuario Nombre del usuario que ejecuta el comando 'bor'.
    * @param password Contraseña del usuario.
    * @param archivo Nombre del archivo a subir.
    * 
    * @throws RemoteException Error en caso de invocación de método remoto.
    * @throws NoAutenticadoException Error en caso de usuario no autenticado.
    * @throws NoPropietarioException Error en caso de usuario no propietario
    * del archivo a borrar.
    * @throws IOException Error en caso de abrir el archivo.
    */
	public void borrarArchivo(
        String usuario,String clave,String archivo) 
            throws RemoteException, IOException, NoAutenticadoException, 
            NoPropietarioException;
}