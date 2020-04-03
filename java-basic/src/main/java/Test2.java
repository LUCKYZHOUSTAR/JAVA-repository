/**
 * desc:
 *
 * @author zhouchaoqiang
 * @since 2020/4/3 8:45 下午
 */
public class Test2 {

	public static void main(String[] args) {
		//需要对最后一个元素做空判断
		String str = "a,b,c,,";
		String[] ary = str.split(",");
// 预期大于3，结果是3
		System.out.println(ary.length);
	}
}
