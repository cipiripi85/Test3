package TCPPing;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

/**
 * Created by Selma on 27.12.2016..
 */
public class SendMessage extends TimerTask {
    Message m;
    private int port;
    private int mps;
    private int size;
    private String hostname;
    private int counter;

    public SendMessage(int port, int mps, int size, String hostname, int counter, Message m) {
        this.port = port;
        this.mps = mps;
        this.size = size;
        this.hostname = hostname;
        this.counter = 0;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(hostname, port);

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            DurationInformation info = new DurationInformation();
            info.SetId(counter);
            info.SetPitcherStartTime();
            info.SetAdditionalData(size, 12);
            m.AddNewSentMsg(counter);
            os.writeObject(info);

            InputStream in = socket.getInputStream();
            ObjectInputStream is = new ObjectInputStream(in);
            DurationInformation returnMessage = (DurationInformation) is.readObject();
            socket.close();

            returnMessage.SetPitcherEndTime();
            m.AddNewReceivedMsg(counter);
            m.CalculateNewValues(returnMessage.GetTotalTime(), returnMessage.GetReceiveTime(), returnMessage.GetSendTime());
        }
         catch ( UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
