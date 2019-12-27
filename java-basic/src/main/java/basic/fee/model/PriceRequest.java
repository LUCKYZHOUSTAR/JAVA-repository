package basic.fee.model;

import java.time.LocalDateTime;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:41
 * @Description:
 */
public class PriceRequest {
    private String callStartTime;
    private String callEndTime;

    private LocalDateTime sTime;

    private LocalDateTime eTime;


    public PriceRequest(String callStartTime, String callEndTime) {
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
    }

    public String getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(String callStartTime) {
        this.callStartTime = callStartTime;
    }

    public String getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(String callEndTime) {
        this.callEndTime = callEndTime;
    }

}
