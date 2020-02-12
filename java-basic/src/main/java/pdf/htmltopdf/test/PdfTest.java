package pdf.htmltopdf.test;

import pdf.htmltopdf.HtmlToPdf;
import pdf.htmltopdf.HtmlToPdfObject;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2020/2/7 09:30
 * @Description:
 */
public class PdfTest {

    public static void main(String[] args) {
//        boolean success = HtmlToPdf.create()
//                .object(HtmlToPdfObject.forHtml("<p><em>Apples</em>, not oranges</p>"))
//                .convert("/Users/zhou/ZHOU/file.pdf");
//        System.out.println(success);


        boolean success = HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("https://www.baidu.com"))
                .convert("/Users/zhou/ZHOU/file.pdf");
    }
}
