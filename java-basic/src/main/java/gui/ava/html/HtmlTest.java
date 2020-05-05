package gui.ava.html;

import gui.ava.html.image.generator.HtmlImageGenerator;

public class HtmlTest {

    public static void main(String[] args) {
        String sb = "<!DOCTYPE html>\n" +
                "<!-- saved from url=(0038)http://localhost:8080/public/test.html -->\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "\n" +
                "    <title>付款凭证</title>\n" +
                "</head>\n" +
                "<body style=\"width: 980px; height: 660px;\">\n" +
                "<table style=\"width: 978px; height: 650px; text-align: center; font-size: 16px; table-layout: fixed; border: 1px solid rgb(0, 0, 0); border-collapse: collapse;\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td colspan=\"11\" style=\"border: 1px solid rgb(0, 0, 0);\">理房通支付电子回执单</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易日期：</td>\n" +
                "        <td colspan=\"3\" style=\"border: 1px solid rgb(0, 0, 0);\">2020.02.06 10:00:00</td>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易流水号：</td>\n" +
                "        <td colspan=\"4\" style=\"border: 1px solid rgb(0, 0, 0);\">LFT0000000000</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" rowspan=\"3\" style=\"border: 1px solid rgb(0, 0, 0);\">付款信息：</td>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0); text-align: left;\">\n" +
                "            <div style=\"display: inline-block; margin-left: 8px;\">名称：</div>\n" +
                "            <div style=\"display: inline-block; width: 85%; text-align: center;\">LFT0000000000</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0); text-align: left;\">\n" +
                "            <div style=\"display: inline-block; margin-left: 8px;\">账号：</div>\n" +
                "            <div style=\"display: inline-block; width: 85%; text-align: center;\">wx_1234567890</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0); text-align: left;\">\n" +
                "            <div style=\"display: inline-block; margin-left: 8px;\">渠道：</div>\n" +
                "            <div style=\"display: inline-block; width: 85%; text-align: center;\">微信渠道2</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" rowspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">收款信息：</td>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0); text-align: left;\">\n" +
                "            <div style=\"display: inline-block; margin-left: 8px;\">名称：</div>\n" +
                "            <div style=\"display: inline-block; width: 85%; text-align: center;\">北京市XXX房产有限公司</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0); text-align: left;\">\n" +
                "            <div style=\"display: inline-block; margin-left: 8px;\">账号：</div>\n" +
                "            <div style=\"display: inline-block; width: 85%; text-align: center;\">123EHP00000000001</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易附言：</td>\n" +
                "        <td colspan=\"9\" style=\"border: 1px solid rgb(0, 0, 0);\">窦乐天333支付诚意金</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易金额小写：</td>\n" +
                "        <td colspan=\"5\" style=\"border: 1px solid rgb(0, 0, 0);\">￥3,000,000</td>\n" +
                "        <td colspan=\"4\" rowspan=\"3\" style=\"position: relative; border: 1px solid rgb(0, 0, 0);\">\n" +
                "            盖章处\n" +
                "            <img \n" +
                "                 style=\"position: absolute; bottom: -40px; right: 80px;\"\n" +
                "                 alt=\"\"/></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易金额大写：</td>\n" +
                "        <td colspan=\"5\" style=\"border: 1px solid rgb(0, 0, 0);\">壹佰万</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"2\" style=\"border: 1px solid rgb(0, 0, 0);\">交易结果：</td>\n" +
                "        <td colspan=\"5\" style=\"border: 1px solid rgb(0, 0, 0);\">支付成功</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"11\" rowspan=\"2\"\n" +
                "            style=\"text-align: left; font-size: 14px; font-family:PingFang SC,Helvetica Neue,Helvetica,Hiragino Sans GB,Microsoft YaHei,SimSun,sans-serif; border: 1px solid rgb(0, 0, 0);\">\n" +
                "            <div style=\"display: inline-block; margin-left: 20px; vertical-align: top;\">说明：</div>\n" +
                "            <div style=\"display: inline-block;\">\n" +
                "                1、每笔交易的交易日期和交易流水号唯一，请仔细辨别，避免重复打印。<br>\n" +
                "                2、本回执单仅用于确认该笔交易的支付信息，理房通不对该笔交易信息准确性及后续结果承担任何责任。<br>\n" +
                "                3、因部分交易渠道的信息传输原因，部分字段出现为空的情况属于正常情况，不属于实际支付信息的确认。\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//		imageGenerator
//				.loadHtml("<b>Hello World!</b> Please goto <a title=\"Goto Google\" href=\"http://www.google.com\">Google</a>.");
//        imageGenerator.loadUrl("http://mtkplmap2:7001/esdm_web");
        imageGenerator.loadHtml(sb);
        imageGenerator.saveAsImage("/Users/zhouchaoqiang/Desktop/hello-world.png");
//        imageGenerator.saveAsHtmlWithMap("hello-world.html","hello-world.png");
    }

}
