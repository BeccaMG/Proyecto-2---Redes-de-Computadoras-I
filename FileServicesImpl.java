/**
 * @file FileServicesImpl.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Objeto remoto correspondiente a los servicios de
 * archivos remotos implementadondo la interfaz FileServices.
 */

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

import com.healthmarketscience.rmiio.*;
	
public class FileServicesImpl 
    extends UnicastRemoteObject implements FileServices {
    
    private Bitacora logs;
    private AuthServices autenticador;
    private Map <String, String> archivosServer;

   /**
    * FileServicesImpl
    * 
    * @brief Constructor.
    *  
    * @throws RemoteException Error en caso de invocación de método remoto.
    */
    public FileServicesImpl() throws RemoteException {
		super();
		logs = new Bitacora();
	}
	
   /**
    * setAutenticador
    * 
    * @brief Fija el objeto remoto de servicios de autenticación.
    * 
    * @param auth Objeto remoto.
    * 
    * @see AuthServices.java
    */ 
	public void setAutenticador(AuthServices auth) {
        this.autenticador = auth;
    }
    
   /**
    * setArchivos
    * 
    * @brief Fija el diccionario de archivos disponibles en el servidor.
    *  
    * @throws RemoteException Error en caso de invocación de método remoto.
    */ 
	public void setArchivos(Map<String,String> files) {
        this.archivosServer = files;
    }
	
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
    public ArrayList <Archivo> archivosEnServidor(String usuario, String clave)
        throws RemoteException, NoAutenticadoException{

        if (!autenticador.autenticado(usuario,clave)) {
            throw new NoAutenticadoException("Usuario no autenticado.");
        }
        
        Comando c = new Comando();
        c.setUsuario(usuario);
        c.setNombre("rls");
        logs.add(c);
        
        ArrayList <Archivo> listaArchivos = new ArrayList <Archivo> ();

        for(Entry <String, String> e : archivosServer.entrySet()) {
            Archivo archAux = new Archivo("", "");
            archAux.setNombre(e.getKey());
            archAux.setPropietario(e.getValue());
            listaArchivos.add(archAux);
        }

        return listaArchivos;
    }
	
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
	public boolean subirArchivo (
        String usuario,String clave,String archivo,RemoteInputStream fileData)
            throws RemoteException, IOException, NoAutenticadoException {
            
        if (!autenticador.autenticado(usuario,clave)) {
            throw new NoAutenticadoException("Usuario no autenticado.");
        }
        
        Comando c = new Comando();
        c.setUsuario(usuario);
        c.setNombre("sub");
        c.setArgumento(archivo);
        logs.add(c);
        
        /*Si el archivo existe y pertenece a otro usuario.*/
        if (archivosServer.containsKey(archivo)) {
            return false;
        } else {  /*de lo contrario.*/
        
            InputStream file = RemoteInputStreamClient.wrap(fileData);
            OutputStream outputStream = new FileOutputStream(archivo);
            
            int data = file.read();
            while(data != -1) {
                outputStream.write((char) data);
                data = file.read();
            }
            file.close();
            outputStream.close();
            //añadir el archivo a la lista.
            archivosServer.put(archivo, usuario);
            return true;
        }
    }
    
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
	public RemoteInputStream bajarArchivo (
        String usuario,String clave,String archivo) 
            throws RemoteException, IOException, NoAutenticadoException { 
           
        if (!autenticador.autenticado(usuario,clave)) {
            throw new NoAutenticadoException("Usuario no autenticado.");
        }
        
        Comando c = new Comando();
        c.setUsuario(usuario);
        c.setNombre("baj");
        c.setArgumento(archivo);
        logs.add(c);
        
        /*Si el archivo existe...*/
        if (archivosServer.containsKey(archivo)) {
            RemoteInputStreamServer istream = null;
            istream = new GZIPRemoteInputStream(new BufferedInputStream(
                                                    new FileInputStream(archivo)));
            
            RemoteInputStream result = istream.export();
            return result;
        } else {/*De lo contrario...*/
            throw new FileNotFoundException("El archivo que desea " +
                "bajar no existe en el servidor.");
        }
    }
    
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
    public void borrarArchivo (
        String usuario,String clave,String archivo) 
            throws RemoteException, IOException, NoAutenticadoException,
                NoPropietarioException {
            
        if (!autenticador.autenticado(usuario,clave)) {
            throw new NoAutenticadoException("Usuario no autenticado.");
        }
        
        Comando c = new Comando();
        c.setUsuario(usuario);
        c.setNombre("bor");
        c.setArgumento(archivo);
        logs.add(c);
        
        /*Si el archivo existe y pertenece a otro usuario.*/
        if (!archivosServer.containsKey(archivo)) {
            throw new FileNotFoundException("El archivo que desea " +
                "borrar no existe en el servidor.");
        } else if (!archivosServer.get(archivo).equals(usuario)) {
            throw new NoPropietarioException("Usted no es propietario " +
                "del archivo que desea borrar.");
        
        } else { /*de lo contrario...*/
            /*Si existe el archivo...*/
            if (archivosServer.containsKey(archivo)) {

                File file = new File(archivo);
            
                if (!file.delete()) {
                    throw new IOException("No se pudo eliminar el archivo.");
                }
                //remover archivo.
                archivosServer.remove(archivo);

            } else { /*si el archivo no existe.*/
            
            }
        }
    }

   /**
    * imprimirBitacora
    * 
    * @brief Imprime los últimos 20 comandos enviados al servidor.
    */ 
    public void imprimirBitacora() {
        logs.print();
    }
}   