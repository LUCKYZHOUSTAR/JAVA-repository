package cmc.lucky.basic.transportion.server.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:08 2017/10/26
 */
public class RemotingException extends Exception {

    private static final long serialVersionUID = -5690687334570505110L;

    public RemotingException() {
        super();
    }

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(Throwable cause) {
        super(cause);
    }
}
