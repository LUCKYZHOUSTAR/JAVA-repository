package cmc.netty.util;

import io.netty.util.Recycler;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:45 2018/1/18
 */
public class RecyclerTest {
    private static final Recycler<RecyclerTest> RECYCLER = new Recycler<RecyclerTest>() {
        @Override
        protected RecyclerTest newObject(Handle<RecyclerTest> handle) {
            return new RecyclerTest(handle);
        }
    };


    private final Recycler.Handle<RecyclerTest> handle;

    private int myFieldA;

    private String myFieldB;

    private RecyclerTest(Recycler.Handle<RecyclerTest> handle) {

        this.handle = handle;

    }

    public static RecyclerTest newInstance(int a, String b) {

        RecyclerTest obj = RECYCLER.get();

        obj.myFieldA = a;

        obj.myFieldB = b;

        return obj;

    }


    public void recycle() {

        myFieldA = 0;

        myFieldB = null;

        handle.recycle(this);

    }

}
