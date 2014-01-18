/**
 * @file a_rmifs.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Clase de Correctas invocaciones a los programas.
 */

class CorrectaInvocacion {

   /**
    * correctaInvocacionAuthServer
    * 
    * @brief Imprime en pantalla la correcta invocación del servidor de
    * autenticacion.
    * 
    * @see a_rmifs
    */ 
	public void correctaInvocacionAuthServer() {
		String a1 = "\nCorrecta Invocación del programa:\n";
		String a2 = "\n\t\t";
		String a3 = "java a_rmifs -f usuarios -p puerto\n\n\n";
		String a4 = "Donde \"usuarios\" es el archivo con usuarios y claves;";
		String a5 = " y \"puerto\" es el puerto del servidor.";
		
		System.out.println(a1 + a2 + a3 + a4 + a5);
	
		System.exit(0);	
	}

   /**
    * correctaInvocacionFileServer
    * 
    * @brief Imprime en pantalla la correcta invocación del servidor de
    * archivos.
    * 
    * @see s_rmifs
    */  
	public void correctaInvocacionFileServer() {
		String a1 = "\nCorrecta Invocación del programa:\n";
		String a2 = "\n\t\t";
		String a3 = "java s_rmifs -l puertolocal -h host -r puerto\n\n\n";
		String a4 = "Donde \"puertolocal\" es puerto referente al servidor";
		String a5 = " de archivos. \"host\" es el nombre DNS o direccion IP";
		String a6 = " del servidor de autenticacion y puerto es el puerto";
		String a7 = " del servidor de autenticacion"; 
	
		System.out.println(a1 + a2 + a3 + a4 + a5 + a6 + a7);
	
		System.exit(0);
	}

   /**
    * correctaInvocacionCliente
    * 
    * @brief Imprime en pantalla la correcta invocación del cliente.
    * 
    * @see c_rmifs
    */ 
	public void correctaInvocacionCliente() {
		String a1 = "\nCorrecta Invocación del programa:\n";
		String a2 = "\n\t\t";
		String a3 = "java c_rmifs [-f usuarios] -m servidor -p puerto "; 
		String a4 = "[-c comandos]\n\n\n";
		String a5 = "Donde \"usuarios\" es el archivo con el usuario y clave."; 
		String a6 = " \"servidor\" es el nombre DNS o direccion IP del";
		String a7 = "  servidor de archivos. \"puerto\" es el puerto del ";
		String a8 = "servidor de archivos y \"comandos\" es el archivo de ";
		String a9 = " comandos iniciales.";
		
		System.out.println(a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9);
		
		System.exit(0);
	}

   /**
    * unParametro
    * 
    * @brief Imprime en pantalla un mensaje indicando que el comando necesita un
    * único parametro.
    */
	public void unParametro() {
		String a1 = "\tEl comando que ha utilizado debe llevar un único";
		String a2 = " parámetro. Use 'info' para mayor información.\n";
		System.out.println(a1 + a2);
	}

   /**
    * sinParametro
    * 
    * @brief Imprime en pantalla un mensaje indicando que el comando no lleva
    * parámetros.
    */ 
	public void sinParametro() {
		String a1 = "\tEl comando que ha utilizado no debe llevar parámetro";
		String a2 = " alguno. Use 'info' para mayor información.\n";
		System.out.println(a1 + a2);
	}
    
   /**
    * informacion
    * 
    * @brief Imprime en pantalla la lista de comandos disponibles del cliente.
    */ 
	public void informacion() {
		String a1 = "\n\n\tComandos disponibles: \n\n";
		String a2 = "\trls -> muestra lista de archivos ";
		String a3 = "disponibles en el servidor centralizado.\n";
		String a4 = "\tlls -> muestra lista de archivos disponibles localmente.\n";
		String a5 = "\tsub <file> -> sube el archivo <file> al servidor remoto.\n";
		String a6 = "\tsub <file> -> baja el archivo <file> desde el servidor remoto.\n";
		String a7 = "\tbor <file> -> borra el archivo <file> en el servidor remoto.\n";
		String a8 = "\tsal-> sale del programa.\n\n";

		System.out.println(a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8);
	}

   /**
    * noEsComando
    * 
    * @brief Imprime en pantalla un mensaje indicando que lo introducido por el
    * cliente no es un comando.
    */ 
	public void noEsComando() {
		String a1 = "\n\nEl comando que ha introducido no existe.\n";
		String a2 = "Para informacion sobre los comandos, introduzca 'info'.";

		System.out.println(a1 + a2);
	}
}