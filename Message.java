package TCPPing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Selma on 23.12.2016..
 */
public class Message {
    Integer _messageCountTotal = 0;
    Integer _messagePerSecond = 0;
    float _averageTimePerScond  = 0;
    float _averageTimeFromCtoP = 0;
    float _averageTimeFromPtoC = 0;
    float _maxTotalTime = 0;
    List<Integer> _sentMessages = new ArrayList<Integer>();
    List<Integer> _receivedMessages = new ArrayList<Integer>();

    public String GetFormattedDate() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = sdfTime.format(new Date());
        return formattedTime;
    }

    public void SetMessageCount(){
            _messageCountTotal = _messageCountTotal + _messagePerSecond;
    }
    public int GetMessageCount(){return _messageCountTotal;}

    public void SetPerSecond (){ _messagePerSecond++; }
    public int GetPerSecond (){return _messagePerSecond;}
    public void ResetPerSecondCount() {_messagePerSecond = 0;}

    public float GetAverageTimePerSecond () {return _averageTimePerScond;}
    public void SetAverageTimePerSecond(double totalTime){  _averageTimePerScond = (float)(_averageTimePerScond + totalTime)/_messagePerSecond; }
    public void ResetAverageTimePerSecond(){_averageTimePerScond = 0;}

    public float GetAverageTimeFromCtoP () {return _averageTimeFromCtoP;}
    public void SetAverageTimeFromCtoP(double totalTime){  _averageTimeFromCtoP = (float)(_averageTimeFromCtoP + totalTime)/_messagePerSecond; }
    public void ResetAverageTimeFromCtoP(){_averageTimeFromCtoP = 0;}

    public float GetAverageTimeFromPtoC () {return _averageTimeFromPtoC;}
    public void SetAverageTimeFromPtoC(double totalTime){  _averageTimeFromPtoC = (float)(_averageTimeFromPtoC + totalTime)/_messagePerSecond; }
    public void ResetAverageTimeFromPtoC(){_averageTimeFromPtoC = 0;}

    public void SetMaxTotalTime(float newTotalTime)
    {
        if(_maxTotalTime == 0){
            _maxTotalTime = newTotalTime;
        }
        else if(newTotalTime > _maxTotalTime){
            _maxTotalTime = newTotalTime;
        }
    }
    public float GetMaxTotalTime() { return _maxTotalTime;}

    public void CalculateNewValues(float totalTime, float receiveTime, float sendTime)
    {
        SetPerSecond();
        SetAverageTimePerSecond(totalTime);
        SetAverageTimeFromCtoP(receiveTime);
        SetAverageTimeFromPtoC(sendTime);
        SetMaxTotalTime(totalTime);
    }

    public void ResetValues()
    {
        ResetPerSecondCount();
        ResetAverageTimePerSecond();
        ResetAverageTimeFromCtoP();
        ResetAverageTimeFromPtoC();
        ResetMessages();
    }

    public void AddNewReceivedMsg(Integer id)
    {
        _receivedMessages.add(id);
    }

    public void AddNewSentMsg(Integer id)
    {
        _sentMessages.add(id);
    }

    public void ResetMessages(){
        _receivedMessages = new ArrayList<Integer>();
        _sentMessages = new ArrayList<Integer>();
    }

    //todo: fix ConcurrentModificationException
    public ArrayList<Integer> RetrieveLostMsg(){
        ArrayList<Integer> lostMessages = new ArrayList<Integer>();

        for(int id: _sentMessages)
        {
            if(!_receivedMessages.contains(id)){
                lostMessages.add(id);
            }
        }
        return lostMessages;
    }

    public void DisplayMessage()
    {
        String newLine = System.lineSeparator();
        System.out.println(GetFormattedDate() + " Total msg: " + GetMessageCount() + " Total msg per sec: " + GetPerSecond() + " Max total time: " + GetMaxTotalTime() + newLine +
                           "Avg time per sec: " + GetAverageTimePerSecond() + " Avg send time: " + GetAverageTimeFromPtoC() + " Avg recieve time: " + GetAverageTimeFromCtoP() + newLine
                            + CreateMessageForLostSockets() + newLine);
    }

    private String CreateMessageForLostSockets(){
        String lostMsg = "";
        ArrayList<Integer> lostMessages = RetrieveLostMsg();
        if(lostMessages.size() > 0) {
            lostMsg = "Lost Messages: ";
            for (int id : lostMessages) {
                lostMsg += id + ", ";
            }
        }
        return lostMsg;
    }




}
