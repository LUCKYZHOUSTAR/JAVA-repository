package com.lucky.servlet;

import com.lucky.util.PrintUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:56 2017/12/1
 */
public class MyServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("servlet init...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req);
        doHttpHeader(req);

        doGetHead(req, resp);
//        super.service(req, resp);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("destroy...");
    }


    private void doRequest(HttpServletRequest request) {
        System.out.println(request.getParameter("name"));
        System.out.println(request.getAttribute("name"));
        System.out.println(request.getParameterNames());
        System.out.println(request.getParameterValues("name"));
    }


    /**
     * http://www.runoob.com/servlet/servlet-client-request.html
     *
     * @param request
     */
    private void doHttpHeader(HttpServletRequest request) {

        System.out.println("返回一个数组，包含客户端发送该请求的所有的 Cookie 对象");
        for (int i = 0; i < request.getCookies().length; i++) {
            System.out.println("cookie" + i + "cookie" + PrintUtil.objToStr(request.getCookies()[i]));
        }

        System.out.println("返回一个枚举，包含提供给该请求可用的属性名称。");
        System.out.println(PrintUtil.objToStr(request.getAttributeNames()));

        System.out.println("返回一个枚举，包含在该请求中包含的所有的头名。。");
        System.out.println(PrintUtil.objToStr(request.getHeaderNames()));


        System.out.println("返回一个 String 对象的枚举，包含在该请求中包含的参数的名称。");
        System.out.println(PrintUtil.objToStr(request.getParameterNames()));

        System.out.println("返回与该请求关联的当前 session 会话，或者如果请求没有 session 会话，则创建一个。");
        System.out.println(PrintUtil.objToStr(request.getSession()));
        System.out.println("基于 Accept-Language 头，返回客户端接受内容的首选的区域设置。");
        System.out.println(PrintUtil.objToStr(request.getLocale()));

        System.out.println("基于 Accept-Language 头，返回客户端接受内容的首选的区域设置。");
//        System.out.println(PrintUtil.objToStr(request.getInputStream()));

        System.out.println("返回用于保护 Servlet 的身份验证方案的名称，例如，\"BASIC\" 或 \"SSL\"，如果JSP没有受到保护则返回 null。");
        System.out.println(PrintUtil.objToStr(request.getAuthType()));

        System.out.println("返回请求主体中使用的字符编码的名称。");
        System.out.println(PrintUtil.objToStr(request.getCharacterEncoding()));

        System.out.println("返回请求主体的 MIME 类型，如果不知道类型则返回 null。。");
        System.out.println(PrintUtil.objToStr(request.getContentType()));


        System.out.println("返回指示请求上下文的请求 URI 部分。");
        System.out.println(PrintUtil.objToStr(request.getContextPath()));


        System.out.println("返回请求的 HTTP 方法的名称，例如，GET、POST 或 PUT。");
        System.out.println(PrintUtil.objToStr(request.getMethod()));

        System.out.println("当请求发出时，返回与客户端发送的 URL 相关的任何额外的路径信息。");
        System.out.println(PrintUtil.objToStr(request.getPathInfo()));


        System.out.println("返回请求协议的名称和版本。");
        System.out.println(PrintUtil.objToStr(request.getProtocol()));


        System.out.println("返回包含在路径后的请求 URL 中的查询字符串。。");
        System.out.println(PrintUtil.objToStr(request.getQueryString()));

        System.out.println("返回发送请求的客户端的互联网协议（IP）地址。");
        System.out.println(PrintUtil.objToStr(request.getRemoteAddr()));


        System.out.println("返回发送请求的客户端的完全限定名称。");
        System.out.println(PrintUtil.objToStr(request.getRemoteHost()));

        System.out.println("如果用户已通过身份验证，则返回发出请求的登录用户，或者如果用户未通过身份验证，则返回 null。。");
        System.out.println(PrintUtil.objToStr(request.getRemoteUser()));


        System.out.println("从协议名称直到 HTTP 请求的第一行的查询字符串中，返回该请求的 URL 的一部分。");
        System.out.println(PrintUtil.objToStr(request.getRequestURI()));


        System.out.println("返回由客户端指定的 session 会话 ID。");
        System.out.println(PrintUtil.objToStr(request.getRequestedSessionId()));

        System.out.println("返回调用 JSP 的请求的 URL 的一部分");
        System.out.println(PrintUtil.objToStr(request.getServletPath()));

        System.out.println("返回一个布尔值，指示请求是否使用安全通道，如 HTTPS。");
        System.out.println(PrintUtil.objToStr(request.isSecure()));


        System.out.println("字节为单位返回请求主体的长度，并提供输入流，或者如果长度未知则返回 -1。");
        System.out.println(PrintUtil.objToStr(request.getContentLength()));

        System.out.println("返回接收到这个请求的端口号。");
        System.out.println(PrintUtil.objToStr(request.getServerPort()));
    }


    private void doGetHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果不存在 session 会话，则创建一个 session 对象
        HttpSession session = request.getSession(true);
        // 获取 session 创建时间
        Date createTime = new Date(session.getCreationTime());
        // 获取该网页的最后一次访问时间
        Date lastAccessTime = new Date(session.getLastAccessedTime());

        //设置日期输出的格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String title = "Servlet Session 实例 - 菜鸟教程";
        Integer visitCount = new Integer(0);
        String visitCountKey = new String("visitCount");
        String userIDKey = new String("userID");
        String userID = new String("Runoob");

        // 检查网页上是否有新的访问者
        if (session.isNew()) {
            title = "Servlet Session 实例 - 菜鸟教程";
            session.setAttribute(userIDKey, userID);
        } else {
            visitCount = (Integer) session.getAttribute(visitCountKey);
            visitCount = visitCount + 1;
            userID = (String) session.getAttribute(userIDKey);
        }
        session.setAttribute(visitCountKey, visitCount);

        //丢掉整个回话
        session.invalidate();
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<h2 align=\"center\">Session 信息</h2>\n" +
                "<table border=\"1\" align=\"center\">\n" +
                "<tr bgcolor=\"#949494\">\n" +
                "  <th>Session 信息</th><th>值</th></tr>\n" +
                "<tr>\n" +
                "  <td>id</td>\n" +
                "  <td>" + session.getId() + "</td></tr>\n" +
                "<tr>\n" +
                "  <td>创建时间</td>\n" +
                "  <td>" + df.format(createTime) +
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>最后访问时间</td>\n" +
                "  <td>" + df.format(lastAccessTime) +
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>用户 ID</td>\n" +
                "  <td>" + userID +
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>访问统计：</td>\n" +
                "  <td>" + visitCount + "</td></tr>\n" +
                "</table>\n" +
                "</body></html>");
    }

    private void doResponse(HttpServletResponse response) {
        //http://www.runoob.com/servlet/servlet-server-response.html
    }

    private void setErrorStatus(HttpServletResponse response) throws ServletException, IOException {
        // 设置错误代码和原因
        response.sendError(407, "Need authentication!!!");

        /**public void sendRedirect(String url)
         该方法生成一个 302 响应，连同一个带有新文档 URL 的 Location 头。*/
    }


    private void setCookie() {

    }
}
