package com.qisi.image;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.fit.cssbox.demo.ImageRenderer;
import org.fit.cssbox.layout.Dimension;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.AWTFontResolver;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;
import org.xml.sax.SAXException;

import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

public class Html2ImageUtil {
    public static String html;
    private static String path;

    static {
        InputStream in = Html2ImageUtil.class.getResourceAsStream("/template.html");
        //定义一个内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] bt = new byte[1024];
        while (true) {
            try {
                if (!((len = in.read(bt)) != -1)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(bt, 0, len);
        }
        html = new String(out.toByteArray());
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        System.out.println(os);
        if (os.startsWith("mac")){
            path="/Users/hkc/Desktop";
        }else if(os.startsWith("linux")){
            path="/home/qisi";
        }else {
            path="D:";
        }
    }

    public static void main(String[] args) throws Exception{
//        html2Image(html);
        xhtmlrenderer(html);
//        html2ImageCustomer(html);
//        cssBox(html);
    }

    public static void html2Image(String html) {
        HtmlImageGenerator hig = new HtmlImageGenerator();
        hig.loadHtml(html);
        File file = new File(path+File.separator+"html2Image" + UUID.randomUUID() + ".png");
        hig.saveAsImage(file);
    }

    public static void html2ImageCustomer(String html) {
        Html2ImageHandler hig = new Html2ImageHandler();
        hig.loadHtml(html);
        HTMLDocument document =(HTMLDocument) hig.editorPane.getDocument();
        StyleSheet defaultStyleContext = document.getStyleSheet();
        Style defaultStyle = defaultStyleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Font font_attribute_key =(Font) defaultStyle.getAttribute("FONT_ATTRIBUTE_KEY");
        if (font_attribute_key==null){
            defaultStyle.addAttribute("FONT_ATTRIBUTE_KEY",Html2ImageHandler.font);
        }
        File file = new File(path+File.separator+"html2ImageCustomer" + UUID.randomUUID() + ".png");
        hig.saveAsImage(file);
    }

    public static void xhtmlrenderer(String html) throws Exception{
        byte[] bytes = html.getBytes("UTF-8");
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(bin);
        Java2DRenderer renderer = new Java2DRenderer(document, 1920,14364);
        AWTFontResolver fontResolver = (AWTFontResolver) renderer.getSharedContext().getFontResolver();
        fontResolver.setFontMapping("PingFang-SC",Html2ImageHandler.font);
        BufferedImage img = renderer.getImage();
        FSImageWriter imageWriter = new FSImageWriter();
        try {
            imageWriter.write(img, path+File.separator+"xhtmlrenderer" + UUID.randomUUID() + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个我没有深入研究，包括字体啊，样式啊，大小
     * @param html
     * @throws IOException
     */
    public static void cssBox(String html) throws IOException {
        ImageRenderer render = new ImageRenderer();
        String url = Html2ImageUtil.class.getResource("/template.html").toString();
        FileOutputStream out = new FileOutputStream(path+File.separator+"cssBox" + UUID.randomUUID() + ".png");
        render.setWindowSize(new Dimension(1980,1000), false);
        try {
            render.renderURL(url, out);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        out.close();
    }
}
