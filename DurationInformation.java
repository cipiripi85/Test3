package TCPPing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by Selma on 23.12.2016..
 */
public class DurationInformation implements Serializable {


    Integer _id; //2-bytes
    long _pitcherStartTime; //8-bytes
    long _pitcherEndTime; // 8-bytes
    long _catcherStartTime; // 8-bytes
    byte[] _additionalData;

    public void SetId(Integer id) {_id = id;}
    public Integer GetId (){return _id;}

    public long GetPitcherStartTime () {return  this._pitcherStartTime;}
    public void SetPitcherStartTime (){this._pitcherStartTime =  System.nanoTime();}

    public long GetPitcherEndTime () {return  this._pitcherEndTime;}
    public void SetPitcherEndTime (){this._pitcherEndTime =  System.nanoTime();}

    public long GetCatcherStartTime () {return  this._catcherStartTime;}
    public void SetCatcherStartTim (){this._catcherStartTime =  System.nanoTime();}

    // calculate time needed to send and receive info
    public float GetSendTime () { return (this._catcherStartTime - this._pitcherStartTime)/1000000.0f;}
    public float GetReceiveTime () { return (this._pitcherEndTime - this._catcherStartTime)/1000000.0f;}
    public float GetTotalTime () { return (this._pitcherEndTime - this._pitcherStartTime)/1000000.0f;}

    public  int GetMessageSize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray().length;
    }

    private static String createDataSize(int msgSize) {
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=1; i<msgSize; i++) {
            sb.append('a');
        }
        return sb.toString();
    }

    public void SetAdditionalData(int size, int usedSize) throws IOException {

            _additionalData = new byte[size-usedSize];
    }

}
