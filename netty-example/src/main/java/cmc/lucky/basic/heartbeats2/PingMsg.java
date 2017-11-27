package cmc.lucky.basic.heartbeats2;

public class PingMsg extends BaseMsg{
    
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }

}
