import java.rmi.*;
import com.healthmarketscience.rmiio.RemoteInputStream;

/** The RMI client will use this interface directly.
 *  The RMI server will make a real remote object that
 *  implements this, then register an instance of it
 *  with some URL.
 */

public interface Rem extends Remote {
  public String getMessage(String fileName, RemoteInputStream remoteFileData) throws RemoteException;

  public String getMessage2() throws RemoteException;
}
