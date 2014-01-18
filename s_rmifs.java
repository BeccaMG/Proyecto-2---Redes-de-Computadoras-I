/**
 * @file s_rmifs.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Servidor de Archivos del sistema.
 */

import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

public class s_rmifs {

   /**
    * main
    * 
    * @brief Programa principal.
    * 
    * @param args Lista de argumentos de invocación.
    */
	public static void main (String[] args){

        //-------------------- Procesamiento de argumentos -------------------//
	
		Scanner in = new Scanner(System.in);
		String puerto = "";				//puerto del File Server
		String dirAuthServer = "";		//dirección del Auth Server
		String puertoAuthServer = "";	//puerto del Auth Server
		boolean lflag = false;
		boolean hflag = false;
		boolean rflag = false;
        String comando = "";
		CorrectaInvocacion ci = new CorrectaInvocacion();

		/* Tomando los argumentos de la línea de comandos.*/
		for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-l")) {
            	if (i + 1 >= args.length){
            		ci.correctaInvocacionFileServer();
            	}
                puerto = args[i + 1];
                lflag = true;
            }
            if (args[i].equals("-h")) {
            	if (i + 1 >= args.length){
            		ci.correctaInvocacionFileServer();
            	}
                dirAuthServer = args[i + 1];
                hflag = true;
            }
            if (args[i].equals("-r")) {
            	if (i + 1 >= args.length){
            		ci.correctaInvocacionFileServer();
            	}
                puertoAuthServer = args[i + 1];
                rflag = true;
            }
        }
        /*Validación de entrada...*/
        if(!lflag || !hflag || !rflag){
        	ci.correctaInvocacionFileServer();
        }
        
        //-------------------------- RMI (import) ----------------------------//
        
        AuthServices authServer = null;
        Registry registry;
            
        try {
            registry = LocateRegistry.getRegistry(dirAuthServer, 
                                        Integer.parseInt(puertoAuthServer));
            authServer = (AuthServices) registry.lookup("AuthServices");
        } 
        
        catch (RemoteException re) {
            System.err.println(
                "\nNo se pudo conectar con el servidor:\n" + re);
        } 
        catch (NotBoundException nbe) {
            System.err.println(                
                "\nNo se pudo conectar con el servidor:\n" + nbe);
        }
        
        //-------------------------- RMI (export) ----------------------------//
        
        try {
            FileServicesImpl localObject = new FileServicesImpl();
            registry = LocateRegistry.createRegistry(Integer.parseInt(puerto));
            localObject.setAutenticador(authServer);
            
            File dirActual = new File("."); 
            String[] listaArch = dirActual.list(); 
            Map <String, String> hashFiles = new HashMap <String,String> ();

            // Creación de lista con archivos actuales
            for (int i = 0; i < listaArch.length; i++) {
            
                File auxFile = new File(listaArch[i]);
                if (auxFile.isFile()) {
                    hashFiles.put(listaArch[i], "superuser");
                }
            }
            
            localObject.setArchivos(hashFiles);
            
            registry.rebind("FileServices", localObject);
        
            do {
                System.out.print("fileServer$ ");
                comando = in.nextLine();
                comando = comando.trim();

                if (comando.equals("")) {
                    continue;
                } else if (comando.equals("log")) {
                    System.out.println("\nBitacora de comandos de clientes:\n");
                    localObject.imprimirBitacora();
                    System.out.println();
                } else if (!comando.equals("sal")) {
                    System.out.println("\n\n\t\tNo existe tal comando.\n");
                    System.out.println("\n\n\t\tComandos Disponibles: ");
                    System.out.print("\t\tlog -> muestra bitácora");
                    System.out.println(" de comandos.");
                    System.out.println("\t\tsal -> terminar el fileServer.\n");
                }
            } while (!comando.equals("sal"));
        }
        
        catch (RemoteException re) {
            System.err.println("No se pudo exportar el objeto:\n" + re);
        }
        
        System.exit(0);
	}
}