package basic.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 09:56
 * @Description:一天的算法规则,一天的时间段拆分成23：59~23：59，然后遍历自己的规则段即可
 */
public class OneDayPriceStrategy implements PriceStrategy {


    List<PriceStrategy> priceStrategyList = new ArrayList<>(3);

    public OneDayPriceStrategy() {
        PriceStrategy strategyOne = new NightlyPriceStrategy(0, 700);
        PriceStrategy strategyTwo = new DailyPriceStrategy(700, 1900);
        PriceStrategy strategyThree = new NightlyPriceStrategy(1900, 2359);
        priceStrategyList.add(strategyOne);
        priceStrategyList.add(strategyTwo);
        priceStrategyList.add(strategyThree);

    }

    @Override
    public void calculateCallPrice(PriceRequest priceRequest, PriceResponse priceResponse) {
        //遍历规则，计算区间值信息
        priceStrategyList.forEach(rule -> {
            rule.calculateCallPrice(priceRequest, priceResponse);
        });

        return;
    }

    //例如每个周一匹配的当天规则，可以通过此参数进行扩展操作, 比如每个周一特定规则
    @Override
    public boolean isSupport(String callStartTime, String callEndTime) {
        //后续可以根据扩展进行对应的扩展信息
        return true;
    }


}
