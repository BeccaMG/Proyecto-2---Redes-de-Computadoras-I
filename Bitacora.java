/**
 * @file Bitacora.java
 * @author Luis Fernandes 10-10239 <lfernandes@ldc.usb.ve>
 * @author Rebeca Machado 10-10406 <rebeca@ldc.usb.ve>
 * 
 * Logger del servidor de archivo.
 */
 
import java.util.*;

public class Bitacora {
    
    private ArrayDeque<Comando> logs;
    
   /**
    * Bitacora
    * 
    * @brief Constructor.
    */ 
    public Bitacora() {
        this.logs = new ArrayDeque<Comando>(20);
    }
    
   /**
    * add
    * 
    * @brief Agrega un comando a los logs.
    * 
    * @param c Comando a agregar.
    * 
    * Solo permite 20 logs.
    */ 
    public void add(Comando c) {
        if (logs.size() >= 20) {
            logs.removeLast();
        }
        logs.addFirst(c);
    }
    
   /**
    * print
    * 
    * @brief Imprime los logs.
    */ 
    public void print() {
        int i = 1;
        for (Comando c : logs) {
            System.out.print(Integer.toString(i) + ") ");
            System.out.print("Usuario: " + c.getUsuario() + "\tCommando: " +
                            c.getNombre() + " ");
            if (c.getArgumento() != null) {
                System.out.print(c.getArgumento());
            } 
            System.out.println();
            i++;
        }
    }
}