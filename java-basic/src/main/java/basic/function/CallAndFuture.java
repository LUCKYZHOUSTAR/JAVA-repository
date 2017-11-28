package basic.function;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:33 2017/11/24
 */
public class CallAndFuture {

    public static void main(String[] args) {
        Callable<Integer> callable =CallAndFuture::getResult;
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static  Integer getResult(){
        return new Random().nextInt(100);
    }
}
