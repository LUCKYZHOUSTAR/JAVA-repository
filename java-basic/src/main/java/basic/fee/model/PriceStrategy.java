package basic.fee.model;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:34
 * @Description:
 */
public interface PriceStrategy {


    //如果后续有圣诞节当天的一个规则话，可以根据此参数配置规则的优先级
    default int priority() {
        return 0;
    }


    void calculateCallPrice(PriceRequest priceRequest, PriceResponse priceResponse);

    //当前策略是否支持
    boolean isSupport(String callStartTime, String callEndTime);


}
