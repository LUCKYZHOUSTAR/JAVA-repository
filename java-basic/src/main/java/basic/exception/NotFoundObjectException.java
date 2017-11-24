package basic.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:43 2017/11/16
 */
public class NotFoundObjectException extends FaultException {
    public NotFoundObjectException() {
    }

    public NotFoundObjectException(String objectName, Object objectId) {
        this("Not found {0} with id {1}", new Object[]{objectName, objectId});
    }

    public NotFoundObjectException(String message) {
        super(message);
    }

    public NotFoundObjectException(String messageFormat, Object... messageArgs) {
        super(messageFormat, messageArgs);
    }

    public NotFoundObjectException(int errorCode, String message) {
        super(errorCode, message);
    }

    public NotFoundObjectException(int errorCode, String messageFormat, Object... messageArgs) {
        super(errorCode, messageFormat, messageArgs);
    }

    public NotFoundObjectException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public NotFoundObjectException(Throwable cause) {
        super(cause);
    }
}