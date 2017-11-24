package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:有序数组查找
 * @Date:Create in 16:58 2017/9/11
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private Key[] keys;
    private Value[] vals;
    private int N;


    public BinarySearchST(int capacity) {
        //调整数组大小
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    public int size() {
        return N;
    }


    public Value get(Key key) {

        return null;
    }
}
