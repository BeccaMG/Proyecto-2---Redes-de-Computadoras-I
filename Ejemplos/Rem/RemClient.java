import java.rmi.*; // For Naming, RemoteException, etc.
import java.net.*; // For MalformedURLException
import java.io.*;  // For Serializable interface
import java.rmi.registry.*;
import com.healthmarketscience.rmiio.*;

/** Get a Rem object from the specified remote host.
 *  Use its methods as though it were a local object.
 * @see Rem
 */

public class RemClient {
  public static void main(String[] args) {
  
    System.out.println("main");
    try {
      String host = "127.0.0.1";
        
      int port = 20226;
      System.out.println("despues del port");
      Registry registry  = LocateRegistry.getRegistry(host,port);
      System.out.println("despuest del registry");
      // Get remote object and store it in remObject:
      Rem remObject = (Rem) registry.lookup("Rem");
        System.out.println("holaaa");
      // Call methods in remObject:
//         InputStream fileData = new FileInputStream("hola.txt");
        RemoteInputStreamServer remoteFileData = null;
  
        remoteFileData = new GZIPRemoteInputStream(new BufferedInputStream(new FileInputStream("Proy.pdf")));

        
        remObject.getMessage("MyFile.pdf", remoteFileData.export());
        
        remoteFileData.close();
//         System.out.println("this should go to stdout");
// 
// PrintStream original = System.out;
// System.setOut(new PrintStream(new FileOutputStream("/dev/null")));
// System.out.println("this should go to /dev/null");
// 
// System.setOut(original);
// System.out.println("this should go to stdout"); // This is not getting printed!!!
      
    }
    catch(NullPointerException e){
      System.out.println("WEBOOOOO");
    }
    catch(RemoteException re) {
      System.out.println("RemoteException: " + re);
    }
    catch(NotBoundException nbe) {
      System.out.println("NotBoundException: " + nbe);
    }
    catch(Exception nbe) {
      System.out.println("Error: ");
    }
//    catch(MalformedURLException mfe) {
//      System.out.println("MalformedURLException: "
//                         + mfe);
  //  }
  }
}
