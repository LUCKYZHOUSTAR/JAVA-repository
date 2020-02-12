package pdf.htmltopdf.test;

import sun.jvm.hotspot.debugger.Page;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2020/2/7 23:09
 * @Description:
 */
public class Test5 {

    public static final String FILETYPE_JPG = "jpg";
    public static final String SUFF_IMAGE = "." + FILETYPE_JPG;

    /**
     * 将指定pdf文件的首页转换为指定路径的缩略图
     *
     * @param filepath  原文件路径，例如d:/test.pdf
     * @param imagepath 图片生成路径，例如 d:/test-1.jpg
     * @param zoom      缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     */
    public static void tranfer(String filepath, String imagepath, float zoom)
            throws PDFException, PDFSecurityException, IOException {
        // ICEpdf document class
        Document document = null;

        float rotation = 0f;

        document = new Document();
        document.setFile(filepath);
        // maxPages = document.getPageTree().getNumberOfPages();

        BufferedImage img = (BufferedImage) document.getPageImage(0,
                GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation,
                zoom);

        Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
        ImageWriter writer = (ImageWriter) iter.next();
        File outFile = new File(imagepath);
        FileOutputStream out = new FileOutputStream(outFile);
        ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
        writer.setOutput(outImage);
        writer.write(new IIOImage(img, null, null));
    }
}
