/**
 * @file FileServices.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Excepción que indica que un usuario no es propietario de un archivo que desea
 * borrar.
 */

public class NoPropietarioException extends Exception {

   /**
    * NoPropietarioException
    * 
    * @brief Constructor.
    */ 
    public NoPropietarioException(String message) {
        super(message);
    }
}