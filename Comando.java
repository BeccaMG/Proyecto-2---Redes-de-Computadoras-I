/**
 * @file Comando.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Representa un comando.
 */

public class Comando {
    
    private String usuario;
    private String nombre;
    private String argumento;
    
   /**
    * getUsuario
    * 
    * @brief Devuelve el nombre del usuario que ejecutó el comando.
    * 
    * @return String con el nombre del usuario.
    */ 
    public String getUsuario() {
        return this.usuario;
    }
    
   /**
    * getNombre
    * 
    * @brief Devuelve el nombre del comando.
    * 
    * @return String con el nombre del comando.
    */ 
    public String getNombre() {
        return this.nombre;
    }
    
   /**
    * getArgumento
    * 
    * @brief Devuelve el argumento de ejecución del comando.
    * 
    * @return String con el nombre del argumento.
    */ 
    public String getArgumento() {
        return this.argumento;
    }
    
   /**
    * setNombre
    * 
    * @brief Fija el usuario que ejecuta un comando.
    * 
    * @param usuario Nombre del usuario.
    */ 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
   /**
    * setNombre
    * 
    * @brief Fija el nombre del comando.
    * 
    * @param nombre Nombre del comando.
    */ 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
   /**
    * setArgumento
    * 
    * @brief Fija el argumento de invocación de un comando.
    * 
    * @param argumento Nombre del argumento.
    */ 
    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }
}