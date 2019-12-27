package basic.fee.model;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 13:02
 * @Description:
 */
public abstract class AbstractPriceStrategy implements PriceStrategy {


    @Override
    public boolean isSupport(String callStartTime, String callEndTime) {

        //截取后四位，判断是否在上述的范围内，在的话就支持

        int callStartVal = Integer.parseInt(callStartTime);
        int callEndVal = Integer.parseInt(callEndTime);

        if (callEndVal < ruleStartValue() || callStartVal > ruleEndValue()) {
            return false;
        }

        return true;
    }


    @Override
    public void calculateCallPrice(PriceRequest priceRequest, PriceResponse priceResponse) {

        if (isSupport(priceRequest.getCallStartTime(), priceRequest.getCallEndTime())) {

            int costMinutes = getCostTime(priceRequest.getCallStartTime(), priceRequest.getCallEndTime());
            calculateSinglePrice(costMinutes, priceResponse);
        }
    }

    abstract void calculateSinglePrice(int costTime, PriceResponse priceResponse);


    //计算区间段内的消耗时间
    private int getCostTime(String callStartTime, String callEndTime) {
        int callStartVal = Integer.parseInt(callStartTime);
        int callEndVal = Integer.parseInt(callEndTime);
        if (callStartVal <= ruleStartValue() && callEndVal > ruleStartValue() && callEndVal <= ruleEndValue()) {
            return TimeUtils.minuteBetween("" + ruleStartValue(), callEndTime);
        } else if (callStartVal >= ruleStartValue() && callEndVal <= ruleEndValue()) {
            return TimeUtils.minuteBetween(callStartTime, callEndTime);

        } else if (callStartVal >= ruleStartValue() && callStartVal <= ruleEndValue() && callEndVal >= ruleEndValue()) {
            return TimeUtils.minuteBetween(callStartTime, "" + ruleEndValue());
        }

        return 0;
    }

    abstract int ruleStartValue();

    abstract int ruleEndValue();
}
