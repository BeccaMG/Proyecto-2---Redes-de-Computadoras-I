import java.rmi.*;
import java.net.*;
import java.rmi.registry.*;

/** The server creates a RemImpl (which implements
 *  the Rem interface), then registers it with
 *  the URL Rem, where clients can access it.
 */

public class RemServer {
  public static void main(String[] args) {
    try {
      // create the registry and bind the name and object.
      Registry registry = LocateRegistry.createRegistry(20226);
      RemImpl localObject = new RemImpl();
      System.out.println("antes del rebind");
      registry.rebind("Rem", localObject);
      System.out.println("después del rebind");
    }
//    catch(AlreadyBoundException abe) {
//      System.out.println("AlreadyBoundException: " + abe);
//    }
    catch(Exception re) {
      System.out.println("InterruptedException: " + re);
    }
//     catch(RemoteException re) {
//       System.out.println("RemoteException: " + re);
//     }

    
  }
}
