package basic.fee.model;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:35
 * @Description:白天具体的算法策略 *
 * // A公司的收费方案：
 * * // 1、所有电话分为白天电话和晚上电话。
 * * // 2、白天是从早晨7点到晚上7点，剩下的时间算晚上。
 * * // 3、白天费用：第1分钟5毛，接下来每分钟1毛。
 * * // 4、晚上费用：第1分钟3毛，接下来10分钟总共收1元，其余每分钟1毛。
 * <p>
 * * // 要求：
 * * // 1、完成该需求的设计和实现；
 * * // 2、考虑一定的扩展性；
 * * // 提示：非算法题目。
 * *
 * * 外层使用的时候，就是计算，我白天从几点打电话，打到了几点
 * * 里面的算法，现在分为了白天和夜晚 每一分钟的计算模式都是不一样的，需要让算法的实现细节更加的灵活点
 */
public class NightlyPriceStrategy extends AbstractPriceStrategy {
    //早上七点~晚上7点
    private int ruleStartValue;
    private int ruleEndValue;


    @Override
    void calculateSinglePrice(int costMinute, PriceResponse priceResponse) {

        if (costMinute == 1) {
            priceResponse.setSumFee(priceResponse.getSumFee() + 30);
        } else if (costMinute > 0 && costMinute <= 11) {
            //大于1分钟的计算规则信息

            priceResponse.setSumFee(priceResponse.getSumFee() + 130);

        } else if (costMinute > 11) {
            priceResponse.setSumFee(priceResponse.getSumFee() + 130 + (costMinute - 11) * 10);
        }

        return;
    }

    public NightlyPriceStrategy(int ruleStartValue, int ruleEndValue) {
        this.ruleStartValue = ruleStartValue;
        this.ruleEndValue = ruleEndValue;
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
