package com.java.common.util;

import com.java.common.exception.ErrorMsg;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * HTTP响应工具类
 */
public class ResponseUtil {

    private static final Locale LOCALE_US = Locale.US;

    private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");

    private static final String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * 一般HTTP响应
     *
     * @param status HTTP响应状态码
     * @param obj    返回的对象
     * @return
     */
    public static Response getResponse(int status, Object obj) {
        return Response.status(status).entity(obj)
                .header("Access-Control-Allow-Origin", "*")
                .type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * 一般HTTP响应
     *
     * @param entity ResponseEntity对象
     * @return
     */
    public static Response getResponse(ResponseEntity<?> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity.getBody())
                .type(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    /**
     * 成功的HTTP响应
     *
     * @param obj
     * @return
     */
    public static Response getOkResponse(Object obj) {
        return Response.ok(obj)
                .header("Access-Control-Allow-Origin", "*")
                .type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * 返回图片
     *
     * @param array
     * @return
     */
    public static Response getImageResponse(byte[] array) {
        return Response.ok(array)
                .type("image/jpeg")
                .header("Expires", toDateString(new Date(0)))
                .header("Pragma", "no-cache")
                .header("Cache-Control", "no-cache")
                .build();
    }

    /**
     * 返回图片
     *
     * @param image
     * @return
     * @throws Exception
     */
    public static Response getImageResponse(RenderedImage image) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return getImageResponse(outputStream.toByteArray());
    }

    private static String toDateString(final Date date) {
        SimpleDateFormat df = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);
        df.setTimeZone(GMT_ZONE);
        return df.format(date);
    }

    /**
     * 业务错误的HTTP响应
     *
     * @param code 统一错误码
     * @param msg  错误摘要信息
     * @return
     */
    public static Response getBizErrorResponse(int code, String msg) {
        return makeResponse(new ErrorMsg(code, msg));
    }

    /**
     * 业务错误的HTTP响应
     *
     * @param code   统一错误码
     * @param msg    错误摘要信息
     * @param detail 错误详细信息（一般用于调试）
     * @return
     */
    public static Response getBizErrorResponse(int code, String msg, String detail) {
        return makeResponse(new ErrorMsg(code, msg, detail));
    }

    /**
     * 业务错误的HTTP响应
     *
     * @param errorMsg 异常信息
     * @return
     */
    public static Response getBizErrorResponse(ErrorMsg errorMsg) {
        return makeResponse(errorMsg);
    }


    /**
     * 生成Response对象
     *
     * @param entity body实体
     * @return response对象
     */
    private static Response makeResponse(Object entity) {
        return Response
                .status(400)
                .header("Access-Control-Allow-Origin", "*")
                .entity(entity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
