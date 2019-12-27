package basic.test;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 11:43
 * @Description:
 */
public class PriceResponse {


    private long sumFee;


    public PriceResponse(long sumFee) {
        this.sumFee = sumFee;
    }

    public long getSumFee() {
        return sumFee;
    }

    public void setSumFee(long sumFee) {
        this.sumFee = sumFee;
    }
}
