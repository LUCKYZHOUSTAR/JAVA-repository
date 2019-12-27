package basic.fee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 12:49
 * @Description:对外包装的类信息
 */
public class FeeClient {


    //整天的规则封装
    private List<PriceStrategy> priceStrategyList = new ArrayList<>(1);

    public FeeClient() {

        //按照优先级排序，例如圣诞节当天的活动信息
        PriceStrategy priceStrategy = new OneDayPriceStrategy();
        priceStrategyList.add(priceStrategy);
    }


    //管理组内的整天的策略信息
    public void addPriceStrate(PriceStrategy priceStrategy) {
        priceStrategyList.add(priceStrategy);
    }

    public void removePriceStrate(PriceStrategy priceStrategy) {
        priceStrategyList.remove(priceStrategy);
    }


    public long costFee(LocalDateTime sTime, LocalDateTime eTime) {


        PriceResponse priceResponse = new PriceResponse(0);
        if (Objects.isNull(sTime) || Objects.isNull(eTime)) {
            return 0;
        }

        if (eTime.isBefore(sTime)) {
            throw new IllegalArgumentException("时间不合法");
        }


        long diffDays = TimeUtils.until(sTime, eTime);

        if (diffDays == 0) {

            //计算一天内的费用即可
            calculateTotalFee(sTime, eTime, priceResponse);
            return priceResponse.getSumFee();
        } else if (diffDays > 0) {

            //跨天后，按照天的维度进行拆分

            for (int i = 0; i < diffDays; i++) {
                if (i == 0) {
                    calculateTotalFee(sTime, getDayMaxTime(sTime, i), priceResponse);
                } else if (i == diffDays) {
                    calculateTotalFee(getDayMinTime(sTime, i), eTime, priceResponse);
                } else {
                    calculateTotalFee(getDayMinTime(sTime, i), getDayMaxTime(sTime, i), priceResponse);

                }

            }
        }

        return priceResponse.getSumFee();
    }


    private void calculateTotalFee(LocalDateTime sTime, LocalDateTime eTime, PriceResponse priceResponse) {
        priceStrategyList.forEach(rule -> {

            String ssTime = sTime.getHour() + "" + sTime.getMinute();
            String eeTime = eTime.getHour() + "" + eTime.getMinute();

            PriceRequest priceRequest = new PriceRequest(ssTime, eeTime);

            rule.calculateCallPrice(priceRequest, priceResponse);


        });


    }

    public static void main(String[] args) {
        System.out.println(getDayMinTime(LocalDateTime.now(), 1));
        FeeClient feeClient = new FeeClient();
        LocalDateTime endTime = getDayMaxTime(LocalDateTime.now(), 3);
        long result = feeClient.costFee(LocalDateTime.now(), endTime);

        System.out.println(result);

    }


    private static LocalDateTime getDayMaxTime(LocalDateTime time, int diffDays) {
        LocalDateTime diffTime = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + diffDays, 23, 59, 59);

        return diffTime;
    }

    private static LocalDateTime getDayMinTime(LocalDateTime time, int diffDays) {
        LocalDateTime diffTime = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + diffDays, 0, 0, 0);
        return diffTime;
    }
}
