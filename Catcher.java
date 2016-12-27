package TCPPing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Selma on 23.12.2016..
 */
public class Catcher {
    public  void startCatcher(int port, String ipAddress) {
                ServerSocket ss;
                try {
                    ss = new ServerSocket(port,100, InetAddress.getByName(ipAddress));
                    while(true) {
                        Socket socket = ss.accept();

                        try {
                           ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                           ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                           DurationInformation m = (DurationInformation) is.readObject();
                           m.SetCatcherStartTim();
                           os.writeObject(m);
                       }
                       catch (IOException e) {
                          //todo: handle ex properly
                       }
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
    }
}
