package basic.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:41 2017/11/16
 */
public interface ErrorInfo {
    int getCode();

    String getMessage();


    static ErrorInfo of(int code, String message) {
        return new ErrorInfo.SimpleErrorInfo(code, message);
    }

    public static class SimpleErrorInfo implements ErrorInfo {
        private int code;
        private String message;

        private SimpleErrorInfo(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
