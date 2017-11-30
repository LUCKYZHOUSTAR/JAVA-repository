package basic.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:17 2017/11/30
 */
public class AsycThreadTest {


    public Map<Long, InvokeResult> invokeResultMap = new ConcurrentHashMap<>();


    public static void main(String[] args) throws Exception {
        InvokeResult<String> result = new InvokeResult<>();


        Thread thread = new Thread(new Result(result));

        thread.start();


        //仿照futuretask的方法，该方法会一直阻塞
        System.out.println(result.get());
        System.out.println("获取到结果了");
    }


    static class Result implements Runnable {

        private InvokeResult invokeResult;

        public Result(InvokeResult invokeResult) {
            this.invokeResult = invokeResult;
        }

        @Override
        public void run() {

            try {

                Thread.sleep(3000);
                System.out.println("开始放置结果了");
                invokeResult.set("alskjdfas");
            } catch (Exception e) {

            }
        }
    }
}
