package basic.fee.model;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:35
 * @Description:白天具体的算法策略 * // A公司的收费方案：
 * * // 1、所有电话分为白天电话和晚上电话。
 * * // 2、白天是从早晨7点到晚上7点，剩下的时间算晚上。
 * * // 3、白天费用：第1分钟5毛，接下来每分钟1毛。
 * * // 4、晚上费用：第1分钟3毛，接下来10分钟总共收1元，其余每分钟1毛。
 * * // 要求：
 * * // 1、完成该需求的设计和实现；
 * * // 2、考虑一定的扩展性；
 * * // 提示：非算法题目。
 * *
 * *
 */
public class DailyPriceStrategy extends AbstractPriceStrategy {


    //早上七点~晚上7点
    private int ruleStartValue;

    private int ruleEndValue;

    public DailyPriceStrategy(int ruleStartValue, int ruleEndValue) {
        this.ruleStartValue = ruleStartValue;
        this.ruleEndValue = ruleEndValue;
    }

    @Override
    void calculateSinglePrice(int costMinute, PriceResponse priceResponse) {
        //白天费用：第1分钟5毛，接下来每分钟1毛。

        if (costMinute == 1) {
            priceResponse.setSumFee(priceResponse.getSumFee() + 50);
        } else if (costMinute > 1) {
            priceResponse.setSumFee(priceResponse.getSumFee() + 50 + (costMinute - 1) * 10);
        }
        //大于1分钟的计算规则信息
        return;
    }


    @Override
    public int ruleStartValue() {
        return ruleStartValue;
    }

    @Override
    public int ruleEndValue() {
        return ruleEndValue;
    }


}
