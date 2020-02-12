package basic.fee.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 12:49
 * @Description:对外包装的类信息 //评测题目一:
 * // A公司的收费方案：
 * // 1、所有电话分为白天电话和晚上电话。
 * // 2、白天是从早晨7点到晚上7点，剩下的时间算晚上。
 * // 3、白天费用：第1分钟5毛，接下来每分钟1毛。
 * // 4、晚上费用：第1分钟3毛，接下来10分钟总共收1元，其余每分钟1毛。
 * 18：58：19：03
 * // 要求：
 * // 1、完成该需求的设计和实现；
 * // 2、考虑一定的扩展性；
 * // 提示：非算法题目。
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


    private static LocalDateTime getDayMaxTime(LocalDateTime time, int diffDays) {
        LocalDateTime diffTime = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + diffDays, 23, 59, 59);

        return diffTime;
    }

    private static LocalDateTime getDayMinTime(LocalDateTime time, int diffDays) {
        LocalDateTime diffTime = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth() + diffDays, 0, 0, 0);
        return diffTime;
    }

    /**
     * 功能描述:当天单元测试代码
     * * * 19:55:59~21:55:59
     */
    public static void oneNight() {
        System.out.println(getDayMinTime(LocalDateTime.now(), 1));
        FeeClient feeClient = new FeeClient();
        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 19, 55, 59);

        LocalDateTime endTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 21, 55, 59);

        long result = feeClient.costFee(now, endTime);

        System.out.println(result);

    }

    /**
     * 功能描述:当天单元测试代码
     * 8:55:59~09:55:59
     */
    public static void oneDay() {
        FeeClient feeClient = new FeeClient();
        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 8, 55, 59);

        LocalDateTime endTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 9, 55, 59);

        long result = feeClient.costFee(now, endTime);

        System.out.println(result);

    }

    /**
     * 功能描述:当天单元测试代码
     * * 18:30:59~19:30:59
     */
    public static void oneDayAndNight() {
        FeeClient feeClient = new FeeClient();
        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 18, 30, 59);

        LocalDateTime endTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 19, 30, 59);

        long result = feeClient.costFee(now, endTime);

        System.out.println(result);

    }

    /**
     * 功能描述:当天单元测试代码
     * * 23:30:59~2:30:59
     */
    public static void twoDayAndNight() {
        FeeClient feeClient = new FeeClient();
        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 30, 59);

        LocalDateTime endTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth() + 1, 2, 30, 59);

        long result = feeClient.costFee(now, endTime);

        System.out.println(result);

    }


    public static void main(String[] args) {

//        oneDay();

//        oneNight();
//        oneDayAndNight();
        twoDayAndNight();
    }


}
