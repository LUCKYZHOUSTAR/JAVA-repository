//package lucky.net.rpc.service.data;
//
//import lombok.Getter;
//import lombok.Setter;
//import mtime.lark.net.rpc.RpcError;
//import mtime.lark.net.rpc.RpcException;
//import mtime.lark.net.rpc.simple.encode.SimpleEncoder;
//import mtime.lark.pb.annotation.ProtoField;
//import mtime.lark.util.config.AppConfig;
//import mtime.lark.util.lang.Exceptions;
//import mtime.lark.util.lang.FaultException;
//import mtime.lark.util.lang.StrKit;
//
//import java.util.List;
//
//
//@Getter
//@Setter
//public class SimpleResponseMessage {
//    /**
//     * 是否成功
//     */
//    private boolean success;
//
//    /**
//     * 调用结果
//     */
//    private Object result;
//
//    /**
//     * 错误信息
//     */
//    private String errorInfo;
//
//    /**
//     * 服务器时间
//     */
//    private long serverTime;
//
//
//    /**
//     * 错误代码
//     */
//    private int errorCode;
//
//    /**
//     * 错误详情，如异常堆栈，一般只应该在 DEBUG 模式传播给客户端，便于快速调试
//     */
//    private String errorDetail;
//
//
//    public SimpleResponseMessage() {
//        // for decode
//    }
//
//    public static SimpleResponseMessage success(Object result) {
//        SimpleResponseMessage message = new SimpleResponseMessage();
//        message.setServerTime(System.currentTimeMillis());
//        message.setSuccess(true);
////        message.setResult(SimpleEncoder.encode(result));
//        return message;
//    }
//
//    public static SimpleResponseMessage failed(Throwable e) {
//        SimpleResponseMessage message = new SimpleResponseMessage();
//        message.setServerTime(System.currentTimeMillis());
//
//        if (e instanceof RpcException) {
//            message.setErrorCode(((RpcException) e).getErrorCode());
//        } else if (e instanceof FaultException) {
//            message.setErrorCode(((FaultException) e).getErrorCode());
//        } else {
//            message.setErrorCode(RpcError.SERVER_UNKNOWN_ERROR.value());
//        }
//
//        message.setErrorInfo(e.getMessage());
//        if (StrKit.isBlank(message.getErrorInfo())) {
//            message.setErrorInfo(e.toString());
//        }
//        if (AppConfig.getDefault().isDebugEnabled()) {
//            message.setErrorDetail(Exceptions.getStackTrace(e));
//        }
//
//        return message;
//    }
//
//
//}
//
//
