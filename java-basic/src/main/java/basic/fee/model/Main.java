package basic.fee.model;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:07
 * @Description: //:
 * // A公司的收费方案：
 * // 1、所有电话分为白天电话和晚上电话。
 * // 2、白天是从早晨7点到晚上7点，剩下的时间算晚上。
 * // 3、白天费用：第1分钟5毛，接下来每分钟1毛。
 * // 4、晚上费用：第1分钟3毛，接下来10分钟总共收1元，其余每分钟1毛。
 * // 要求：
 * // 1、完成该需求的设计和实现；
 * // 2、考虑一定的扩展性；
 * // 提示：非算法题目。
 * <p>
 * 外层使用的时候，就是计算，我白天从几点打电话，打到了几点
 * 里面的算法，现在分为了白天和夜晚 每一分钟的计算模式都是不一样的，需要让算法的实现细节更加的灵活点
 * <p>
 * <p>
 * // ：
 * // 	- 一个生产者，多个消费者。
 * // 	- 两种实现：
 * // 		- 用Object原生wait/notify、Thread实现；
 * //		- 用Juc包线程池实现；
 */
public class Main {

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: zhou
     * @date: 2019/12/27 下午9:42
     * 1.每一种算法是一个策略，策略有自己的适用时间范围
     * <p>
     * 1.时间范围在变
     * 2.时间段内的规则也在变化
     * 3.规则只负责我的时间范围内的计算算法，后续方便扩展
     * 4.每个规则，按照一天的维度来计算，不能有半天半天，即使有半天+后续规则，也折算成一个规则模型
     * 5.规则的适用范围也要固定，比如只能是1月1号23：59--1月2号的23：59   7：00-19：00的钱+19：00----23：59的钱
     * 6.一天是一个规则，规则有优先级
     * 7.一天的规则可以由其他几种单一规则的组合
     * <p>
     * ------------------------------------------
     * 1.调用方如何对时间段拆分，拆分成规则所需要的时间段，按照天的维度进行拆分，走规则引擎表达式
     * 2.如果要制定，就要按照规范制定一天的规则，否则不采纳
     */

    public static void main(String[] args) {


        PriceStrategy priceStrategy = new OneDayPriceStrategy();

        PriceRequest priceRequest = new PriceRequest("800", "2130");
        PriceResponse priceResponse = new PriceResponse(0);
        priceStrategy.calculateCallPrice(priceRequest, priceResponse);

        System.out.println(priceResponse);
        System.out.println(priceResponse.getSumFee());
    }
}
