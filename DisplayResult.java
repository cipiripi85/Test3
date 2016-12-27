package TCPPing;

import java.util.TimerTask;

/**
 * Created by Selma on 23.12.2016..
 */
public class DisplayResult extends TimerTask {
    Message _m;
    public DisplayResult(Message m){
        _m = m;
    }
    public void run() {
        _m.SetMessageCount();
        _m.DisplayMessage();
        _m.ResetValues();
    }
}
