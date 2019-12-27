package com.lucky.jedis.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/16 17:10
 * @Description:
 */
public class FunnelRateLimiter {


    static class Funnel {
        //        # 漏斗容量
        int capacity;

        //        # 漏嘴流水速率
        float leakingRate;

        //        # 漏斗剩余空间
        int leftQuota;
        //        # 上一次漏水时间
        long leakingTs;

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
//            # 距离上一次漏水过去了多久
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = (int) (deltaTs * leakingRate);// # 又可以腾出不少空间了
            if (deltaQuota < 0) { // 间隔时间太长，整数数字过大溢出,时间太长，导致int变为了负数
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) { // 腾出空间太小，最小单位是1
                return;
            }
//            # 增加剩余空间
            this.leftQuota += deltaQuota;
//            # 记录漏水时间
            this.leakingTs = nowTs;
//             # 剩余空间不得高于容量
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }

        }


        //        # 判断剩余空间是否足够
        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }

    }


    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1); // 需要1个quota
    }

}
