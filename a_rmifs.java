/**
 * @file a_rmifs.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Servidor de autenticación del sistema.
 */
 
import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

public class a_rmifs {

   /**
    * main
    * 
    * @brief Programa principal.
    * 
    * @param args Lista de argumentos de invocación.
    */
	public static void main (String[] args) {

        //-------------------- Procesamiento de argumentos -------------------//

		String archUsuarios = ""; //archivo de usuarios y claves.
		Map <String, String> dicUserPass = new HashMap <String, String> ();
		// diccionario de usuarios y claves.
		String lineaArch;  // linea leida del archivo de usuarios y claves.
		String puerto = ""; // puerto del servidor.
		boolean pflag = false;
		boolean fflag = false;
		CorrectaInvocacion ci = new CorrectaInvocacion();

		/* Tomando los argumentos de la línea de comandos.*/
		for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p")) {
            	if (i + 1 >= args.length){
            		ci.correctaInvocacionAuthServer();
            	}
                puerto = args[i + 1];
                pflag = true;
            }
            if (args[i].equals("-f")) {
            	if (i + 1 >= args.length){
            		ci.correctaInvocacionAuthServer();
            	}
                archUsuarios = args[i + 1];
                fflag = true;
            }
        }

        if(!pflag || !fflag) {
        	ci.correctaInvocacionAuthServer();
        }

		/*A partir de ahora se añaden los usuarios a la tabla de Hash.*/
		try{
			FileReader lector = new FileReader(archUsuarios);
			BufferedReader lectorBuff = new BufferedReader(lector);
			String[] userPass; //variable auxiliar para separar user y pass.

			while((lineaArch = lectorBuff.readLine()) != null){
				userPass = lineaArch.split(":");
				if (dicUserPass.containsKey(userPass[0]) ||
                    userPass[0].equals("superuser")) {
					System.out.println(userPass[0] + " ya existe.");
				} else {
					dicUserPass.put(userPass[0], userPass[1]);
				}
			}
		}
		
		catch(FileNotFoundException ex) {
            System.out.println("No fue posible abrir '" + archUsuarios + "'");	
        }        
        catch(IOException ex) {
            System.out.println("Error al leer el archivo '" +archUsuarios +"'");
        }
        
        //------------------------------ RMI ---------------------------------//

        Registry registry;

        /*A partir de ahora se manejan los aspectos del objeto remoto.*/
		try {
            registry = LocateRegistry.createRegistry(Integer.parseInt(puerto));
            AuthServicesImpl localObject = new AuthServicesImpl();
			localObject.setDiccionario(dicUserPass);
            registry.rebind("AuthServices", localObject);
		}
		
		catch (RemoteException re) {
            System.err.println("No se pudo exportar el objeto:\n" + re);
        }

	}
}