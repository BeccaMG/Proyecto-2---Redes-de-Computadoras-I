/**
 * @file c_rmifs.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Cliente del sistema.
 */

import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;

import com.healthmarketscience.rmiio.*;

public class c_rmifs { 

   /**
    * main
    * 
    * @brief Programa principal.
    * 
    * @param args Lista de argumentos de invocación.
    */ 
    public static void main(String[] args) {

        //-------------------- Procesamiento de argumentos -------------------//

        Scanner in = new Scanner(System.in);
        String puerto = "";         //puerto del servidor de archivos
        String user = "";   //nombre de usuario (entrada)
        String key = "";    //clave de usuario (entrada)
        String[] comando = new String[2];   //línea de comandos del usuario.
        String server = ""; //nombre dns o ip del servidor de archivos.
        String archComandos = ""; //nombre de archivo de comandos.
        String archUsuario = "";  //archivo con contraseña y nombre de usuario.
        ArrayList <String> archDisponibles = new ArrayList <String> ();
                                //lista de archivos disponibles por el cliente.
        CorrectaInvocacion ci = new CorrectaInvocacion();
        
        boolean pflag = false;
        boolean mflag = false;
        boolean fflag = false;
        boolean cflag = false;

        FileReader lectorComandos;
        BufferedReader lectorBuffComandos = null;

        /* Tomando los argumentos de la línea de comandos.*/
        for (int i = 0; i < args.length; i++) {
        
            if (args[i].equals("-p")) {
                if (i + 1 >= args.length){
                    ci.correctaInvocacionCliente();
                }
                puerto = args[i + 1];
                pflag = true;
            }
            if (args[i].equals("-m")) {
                if (i + 1 >= args.length){
                    ci.correctaInvocacionCliente();
                }
                server = args[i + 1];
                mflag = true;
            }
            if (args[i].equals("-f")) {
                archUsuario = args[i + 1];
                fflag = true;
            } 
            if (args[i].equals("-c")) {
                archComandos = args[i + 1];
                cflag = true;
            }
        }

        if( !mflag || !pflag ) {
            ci.correctaInvocacionCliente();
        }

        if(!fflag){//si el usuario y clave no se introdujeron por archivo...
            while(user.equals("")){
                System.out.print("Introduzca su usuario: ");
                user = in.nextLine();   
            }
            
            while(key.equals("")){
                System.out.print("Introduzca su clave: ");
                key = in.nextLine();
            }
        }else{//si el usuario y clave se introdujeron por archivo...
            try {
                FileReader lector = new FileReader(archUsuario);
                BufferedReader lectorBuff = new BufferedReader(lector);
                String[] userPass; //variable auxiliar para separar user y pass
                String lineaArch = "";

                lineaArch = lectorBuff.readLine();
                userPass = lineaArch.split(":");
                user = userPass[0];
                key = userPass[1];
            }
            
            catch (FileNotFoundException ex) {
                System.err.println(
                    "\nNo existe el archivo '" + archUsuario + "'.\n"); 
                System.exit(0);
            }
            catch(IOException ex) {
                System.err.println(
                    "\nError al leer el archivo '" + archUsuario + "':\n" + ex +
                    "\n");
                System.exit(0);
            }
        }

        //------------------------------ RMI ---------------------------------//

        FileServices fileServer = null;
        Registry registry;
            
        try {
            registry = LocateRegistry.getRegistry(server, 
                                                  Integer.parseInt(puerto));
            fileServer = (FileServices) registry.lookup("FileServices");
        } 
        
        catch (RemoteException re) {
            System.err.println(
                "\nNo se pudo conectar con el servidor:\n" + re + "\n");
            System.exit(0);
        } 
        catch (NotBoundException nbe) {
            System.err.println(                
                "\nNo se pudo conectar con el servidor:\n" + nbe + "\n");
            System.exit(0);
        }
        
        if (cflag) {
            try {
                lectorComandos = new FileReader(archComandos);
                lectorBuffComandos = new BufferedReader(lectorComandos);
            }

            catch (FileNotFoundException ex) {
                System.err.println(
                    "\nNo existe el archivo '" + archComandos + "'.\n"); 
                System.exit(0);
            }
        }

        //----------------------- Ejecución de comandos ----------------------//

        do {
            System.out.print(user+"$ ");
            //Si los comandos vienen por archivo...
            if (cflag) {        
                try {

                    String lineaArch = "";

                    lineaArch = lectorBuffComandos.readLine();
                    if (lineaArch == null) {
                        System.out.println();
                        cflag = false;
                        continue;
                    } else {
                        System.out.println(lineaArch);
                        comando = lineaArch.split(" ");
                    }

                }

                catch(IOException ex) {
                    System.err.println(
                        "\nError al leer el archivo '" + archComandos + "':\n" 
                        + ex + "\n");
                    System.exit(0);
                }

            } else { //Si los comandos no vienen por archivo o ya se leyeron...
               comando = in.nextLine().split(" ");
            }

            try {
                
                if (comando[0].equals("")) {
                    continue;

                //----------------- Lista archivos remotos -------------------//
                    
                    //Listar archivos remotos
                } else if (comando[0].equals("rls")) { 
                    
                    if (comando.length > 1) {
                        ci.sinParametro();
                        continue;
                    }
                    
                    try {

                        ArrayList <Archivo> lista = 
                            fileServer.archivosEnServidor(user, key);
                        Archivo archAux = new Archivo ("", "");
                        String auxStr; //String auxiliar para imprimir.

                        System.out.print("\n\tLa lista de archivos");
                        System.out.print(" en el servidor (archivo -> ");
                        System.out.println("propiertario) es:\n");

                        if (lista.size() == 0) {
                            System.out.print("\n\n\tNo hay archivos ");
                            System.out.println("en el servidor.\n");
                        } else {
                            for(int i = 0; i < lista.size(); i++) {
                                archAux = lista.get(i);
                                System.out.print("\t\t");   
                                auxStr = archAux.getNombre();
                                System.out.print(auxStr);
                                System.out.print(" -> ");
                                auxStr = archAux.getPropietario();
                                System.out.println(archAux.getPropietario() 
                                    + "\n");
                            }
                        }

                    } 
                    
                    catch (RemoteException re) { 
                        System.err.println(
                            "\nNo se pueden listar los archivos remotos:\n"
                                + re + "\n");
                    }
                }
                
                //----------------- Lista archivos locales -------------------//
                
                //Listar archivos locales
                else if (comando[0].equals("lls")) { 
                    
                    if (comando.length > 1) {
                        ci.sinParametro();
                        continue;
                    }

                    /*A partir de ahora se genera la lista de archivos*/

                    File dirActual = new File("."); 
                    // Directorio donde se ejecuta el c_rmifs
                    String[] listaArch = dirActual.list(); 
                    // todos los archivos.
                    archDisponibles = new ArrayList <String> (); 
                    //Inicializa la lista.
                    for(int i = 0; i < listaArch.length; i++) {
                        // Archivo auxiliar para verificar tipo de archivo.
                        File auxFile = new File(listaArch[i]);

                        if (auxFile.isFile()) {
                            archDisponibles.add(listaArch[i]);                
                        }
                    }
                    // Imprime
                    System.out.println("\nSus archivos disponibles son:\n\n");
                    for (int i = 0; i < archDisponibles.size(); i++) {
                        System.out.print("\t\t");
                        System.out.println(archDisponibles.get(i));
                    }

                }
                
                //--------------- Carga archivo en el servidor ---------------//
                
                //Subir archivo al servidor
                else if (comando[0].equals("sub")) { 
                
                    if ((comando.length == 1) || (comando.length > 2)) {
                        ci.unParametro();
                        continue;
                    }
                    
                    if (comando[1].contains("/")) {
                        System.out.println("\nSolo puede enviar archivos " +
                            "contenidos en el directorio desde donde invocó " +
                            "el cliente. Consulte estos archivos con 'lls'.\n");
                        continue;
                    }
                    
                    //Declaro el stream del archivo
                    RemoteInputStreamServer remoteFileData = null;
                    
                    //Creo el stream a partir del archivo dado
                    try {
                        remoteFileData = new GZIPRemoteInputStream(
                            new BufferedInputStream(
                            new FileInputStream(comando[1])));

                        boolean subido; //flag que indica si ha sido subido.
                        //Se sube al servidor con un método remoto
                        subido = fileServer.subirArchivo(user, key, comando[1],
                                            remoteFileData.export());
                        
                        if (!subido) {
                            System.out.println("\nNo es posible subir el " +
                                "archivo dado que ya existe en el servidor.\n");
                        } else {
                            System.out.println("\n\t\tArchivo subido " +
                                "exitosamente.\n");
                        }
                        //Cierro el archivo
                        remoteFileData.close();
                    } 
                    
                    catch (RemoteException re) {
                        System.err.println(
                            "\nNo se puede subir el archivo '" + comando[1] +
                            "':\n" + re + "\n");
                    }
                    catch (FileNotFoundException fnfe) {
                        System.err.println(
                            "\nNo se encuentra el archivo '" + comando[1] +
                            "'.\n");
                    }
                    catch (IOException ioe) {
                        System.err.println(
                            "\nNo se puede abrir el archivo '" + comando[1] +
                            "':\n" + ioe + "\n");
                    }
                }
                
                //-------------- Descarga archivo del servidor ---------------//
                
                else if (comando[0].equals("baj")) {
                
                    if ((comando.length == 1) || (comando.length > 2)) {
                        ci.unParametro();
                        continue;
                    }
                    
                    //Obtengo el archivo del servidor con método remoto
                    try {
                        RemoteInputStream remoteFileData = 
                            fileServer.bajarArchivo(user, key, comando[1]);
                        
                        //Obtengo el stream del archivo
                        InputStream fileData =
                            RemoteInputStreamClient.wrap(remoteFileData);
                        
                        //Creo un stream para copiar el contenido del archivo
                        OutputStream outputStream = 
                            new FileOutputStream(comando[1]);
                        int data = fileData.read();
                        while(data != -1) {
                            outputStream.write((char) data);
                            data = fileData.read();
                        }
                        
                        //Se cierran ambos archivos
                        fileData.close();
                        outputStream.close();
                        
                        System.out.println("\n\t\tArchivo descargado " +
                                                "exitosamente.\n");
                    } 
                    
                    catch (RemoteException re) {
                        System.err.println(
                            "\nNo se puede bajar el archivo '" + comando[1] +
                            "':\n" + re + "\n");
                    }
                    catch (FileNotFoundException fnfe) {
                        System.err.println(
                            "\nNo se encuentra el archivo '" + comando[1] +
                            "'\n");
                    }
                    catch (IOException ioe) {
                        System.err.println(
                            "\nNo se puede abrir el archivo '" + comando[1] +
                            "':\n" + ioe + "\n");
                    }
                }
                
                //--------------- Borra archivo en el servidor ---------------//
                              
                else if (comando[0].equals("bor")) {
                    if((comando.length == 1) || (comando.length > 2)) {
                        ci.unParametro();
                        continue;
                    }           
                    
                    try {
                        fileServer.borrarArchivo(user, key, comando[1]);
                        System.out.println("\n\t\tArchivo borrado " +
                                "exitosamente.\n");
                    }
                    
                    catch (RemoteException re) {
                        System.err.println(
                            "\nNo se puede borrar el archivo '" + comando[1] +
                            "': \n" + re + "\n");
                    }
                    catch (FileNotFoundException ex) {
                        System.err.println(
                            "\nNo se encuentra el archivo '" + comando[1] +
                            "' en el servidor.\n");
                    }
                    catch (IOException ioe) {
                        System.err.println(
                            "\nNo se puede borrar el archivo '" + comando[1] +
                            "':\n" + ioe + "\n");
                    }
                    catch (NoPropietarioException e) {
                        System.err.println(
                            "\nNo puede borrar el archivo '" + comando[1] +
                            "' pues no es su propietario.\n");
                    }
                }
                
                //--------------- Muestra comandos disponibles ---------------//
                
                else if (comando[0].equals("info")) {
                    ci.informacion();
                }
                
                //------------------- Sale del cliente -----------------------//
                
                else if (!comando[0].equals("sal")){ //si el comando no está...
                    ci.noEsComando();
                }
                
            }
            
            catch (NoAutenticadoException e) {
                System.out.println(
                "No está autorizado para realizar esta operación.");
            }

        } while(!comando[0].equals("sal"));
        
        System.out.println("\n\n\tGracias por utilizar nuestro programa.\n\n");
        System.out.println("\t\t\t\tElaborado por:\n");
        System.out.println("\t\t\t\t* Luis Fernandes.");
        System.out.println("\t\t\t\t* Rebeca Machado.\n\n");
    }
}