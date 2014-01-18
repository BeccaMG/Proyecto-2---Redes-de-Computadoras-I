/**
 * @file Archivo.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Representa un archivo.
 */

import java.io.Serializable;

public class Archivo implements Serializable {
        
    private String nombre;
    private String propietario;
    private static final long serialVersionUID = 7526471155622776147L;
    
   /**
    * Archivo
    * 
    * @brief Constructor.
    * 
    * @param name Nombre del archivo.
    * @param owner Propietario del archivo.
    */
    public Archivo(String name, String owner){
        this.nombre = name;
        this.propietario = owner;
    }

   /**
    * getNombre
    * 
    * @brief Devuelve el nombre de un archivo.
    * 
    * @return String con el nombre del archivo.
    */ 
    public String getNombre(){
        return nombre;
    }

   /**
    * setNombre
    * 
    * @brief Fija el nombre de un archivo.
    * 
    * @param nombre Nombre del archivo.
    */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

   /**
    * getPropietario
    * 
    * @brief Devuelve el propietario de un archivo.
    * 
    * @return String con el propietario del archivo.
    */  
    public String getPropietario(){
        return propietario;
    }

   /**
    * setPropietario
    * 
    * @brief Fija el propietario de un archivo.
    * 
    * @param nombre Nombre del propietario.
    */ 
    public void setPropietario(String propietario){
        this.propietario = propietario;
    }
}