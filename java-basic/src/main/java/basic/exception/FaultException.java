package basic.exception;

import org.apache.commons.lang3.text.StrBuilder;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:41 2017/11/16
 */
public class FaultException extends RuntimeException {
    private final int errorCode;
    private Map<String, Object> data;

    public FaultException() {
        this.errorCode = -1;
    }

    public FaultException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public FaultException(String messageFormat, Object... messageArgs) {
        super(MessageFormat.format(messageFormat, messageArgs));
        this.errorCode = -1;
    }

    public FaultException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FaultException(int errorCode, String messageFormat, Object... messageArgs) {
        this(errorCode, MessageFormat.format(messageFormat, messageArgs));
    }

    public FaultException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public FaultException(Throwable cause) {
        super(cause);
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public boolean hasErrorCode() {
        return this.errorCode != 0 && this.errorCode != -1;
    }

    @Override
    public String toString() {
        String s = super.toString();
        if (this.hasErrorCode()) {
            s = s + " (errorCode: " + this.getErrorCode() + ")";
        }

        return s;
    }

    public String toStringDetail() {
        StrBuilder sb = new StrBuilder();
        sb.appendln(this.getStackTraceString()).append(this.getDataString()).appendNewLine();
        return sb.toString();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (this.data != null && this.data.size() > 0) {
            s.println();
            this.data.forEach((k, v) -> {
                s.append("\t").append(k).append(": ");
                s.print(v);
                s.println();
            });
        }

    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        if (this.data != null && this.data.size() > 0) {
            s.println();
            this.data.forEach((k, v) -> {
                s.append("\t").append(k).append(": ");
                s.print(v);
                s.println();
            });
        }

    }

    public String getStackTraceString() {
        return Exceptions.getStackTrace(this);
    }

    public Map<String, Object> getData() {
        if (this.data == null) {
            this.data = new HashMap();
        }

        return this.data;
    }

    public String getDataString() {
        if (this.data != null && this.data.size() > 0) {
            StrBuilder sb = new StrBuilder();
            this.data.forEach((k, v) -> {
                sb.append(k).append(": ").append(v).appendNewLine();
            });
            return sb.toString();
        } else {
            return "";
        }
    }

    public static <T extends ErrorInfo> FaultException of(T error) {
        return new FaultException(error.getCode(), error.getMessage());
    }

    public static <T extends ErrorInfo> FaultException of(T error, Object... messageArgs) {
        return new FaultException(error.getCode(), MessageFormat.format(error.getMessage(), messageArgs));
    }

    public static <T extends ErrorInfo> FaultException of(Throwable cause, T error) {
        return new FaultException(error.getCode(), error.getMessage(), cause);
    }

    public static <T extends ErrorInfo> FaultException of(Throwable cause, T error, Object... messageArgs) {
        return new FaultException(error.getCode(), MessageFormat.format(error.getMessage(), messageArgs), cause);
    }
}
