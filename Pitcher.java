package TCPPing;

import java.util.Timer;

/**
 * Created by Selma on 23.12.2016..
 */
public class Pitcher {
    Message m;
    private int port;
    private int mps;
    private int size;
    private String hostname;
    private int counter;

    public Pitcher(int port, int mps, int size, String hostname) {

        this.port = port;
        this.mps = mps;
        this.size = size;
        this.hostname = hostname;
        this.counter = 0;
        m = new Message();
    }
    public  void startPitcher() {
         Timer timer = new Timer();
         timer.schedule(new DisplayResult(m), 0, 1000);
         counter++;
         timer.schedule(new SendMessage(port, mps, size, hostname, counter, m),0, 1000/mps);
    }

}
