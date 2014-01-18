import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

/** This is the actual implementation of Rem that
 *  the RMI server uses. The server builds an instance
 *  of this then registers it with a URL. The
 *  client accesses the URL and binds the result to
 *  a Rem (not a RemImpl; it doesn't have this).
 */

public class RemImpl extends UnicastRemoteObject implements Rem {
  public String test;
  public RemImpl() throws RemoteException {
    this.test = "WEBO";
  }

  public String getMessage2() throws RemoteException{
    return this.test;
  }

  public String getMessage(String fileName, RemoteInputStream remoteFileData) throws RemoteException,NullPointerException {
    System.out.println("ENTRE");
    try{
//     	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//     	String s = bufferRead.readLine();
//       throw new NullPointerException();
//     }
//     catch(IOException e){
// 	e.printStackTrace();
// 	return null;
//     }
        InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
        OutputStream outputStream = new FileOutputStream(fileName);
//                  
        int data = fileData.read();
        while(data != -1) {
            //do something with data...
            outputStream.write((char) data);
            data = fileData.read();
        }
        fileData.close();
        outputStream.close();
    
    } catch (Exception e) {
        System.out.println("hubo un error");
    }
    return "hola";
  }
}
