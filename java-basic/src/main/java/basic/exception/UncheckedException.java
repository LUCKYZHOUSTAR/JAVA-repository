package basic.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:42 2017/11/16
 */

public class UncheckedException extends RuntimeException {
    public UncheckedException() {
    }

    public UncheckedException(String msg) {
        super(msg);
    }

    public UncheckedException(Throwable inner) {
        super(inner);
    }

    public UncheckedException(String msg, Throwable inner) {
        super(msg, inner);
    }
}
