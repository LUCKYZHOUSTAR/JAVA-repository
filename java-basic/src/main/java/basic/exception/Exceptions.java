package basic.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:41 2017/11/16
 */
public class Exceptions {
    public Exceptions() {
    }

    public static FaultException notFoundObject(String objectName, Object objectId) {
        return new NotFoundObjectException(objectName, objectId);
    }

    public static FaultException notImpl(String methodName) {
        return new FaultException("Not implemented " + methodName);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static FaultException newNotImplemented(String methodName) {
        return new FaultException("Not implemented " + methodName);
    }

    public static FaultException undefinedEnum(Class enumClass, Object value) {
        return new FaultException(String.format("Undefined enum %s: %s", new Object[]{enumClass.getSimpleName(), value}));
    }

    public static FaultException undefinedEnum(String enumName, Object value) {
        return new FaultException(String.format("Undefined enum %s: %s", new Object[]{enumName, value}));
    }

    public static RuntimeException error(String message, Object... args) {
        return new RuntimeException(MessageFormat.format(message, args));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static RuntimeException asRuntime(Exception ex) {
        return !(ex instanceof FaultException) && !(ex instanceof RuntimeException) ? new RuntimeException(ex) : (RuntimeException) ex;
    }

    public static UncheckedException asUnchecked(Throwable ex) {
        return new UncheckedException(ex);
    }

    public static String getStackTrace(Throwable e) {
        Objects.requireNonNull(e, "arg e");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String getMessage(Throwable e) {
        Objects.requireNonNull(e, "arg e");
        String msg = e.getMessage();
        if (msg != null && !msg.isEmpty()) {
            msg = e.getClass().getName();
        }

        return msg;
    }
}
