/**
 * @file FileServices.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Excepción que indica que un usuario no está autenticado en el servidor de
 * archivos.
 */
 
public class NoAutenticadoException extends Exception {

   /**
    * NoAutenticadoException
    * 
    * @brief Constructor.
    */
    public NoAutenticadoException(String message) {
        super(message);
    }
}