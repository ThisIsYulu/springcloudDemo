package com.yulu.common.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yulu on 2020/4/16.
 */
public class PDFUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ByteArrayOutputStream createPdf(Map<String, Object> dataMap, String templateName, String pageSize,
                                           String logoPath, float logoAbsoluteX, float logoAbsoluteY, float logoPercent) {
        Writer writer = new StringWriter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // 获取模板,并设置编码方式
            Configuration configuration = new Configuration();
            configuration.setClassForTemplateLoading(PDFUtil.class, "/pdfTemplates");
            configuration.setDefaultEncoding("UTF-8");
            Template template = configuration.getTemplate(templateName);
            template.process(dataMap, writer);
            writer.flush();
            // 解析HTML模板得到内容
            String content = writer.toString();
            // 生成PDF
            Document document = new Document(PageSize.getRectangle(pageSize));
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("E://" + DateUtils.getTimeStamp() + ".pdf"));// 生成本地文件
//            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);// 输出为流
            document.open();
            if (StringUtils.isNotBlank(logoPath)) {
                byte[] logoBytes = getLogoBytes(logoPath);
                if (Objects.nonNull(logoBytes)) {
                    Image image = Image.getInstance(logoBytes);
                    // 设置位置，以左下角为原点
                    image.setAbsolutePosition(logoAbsoluteX, logoAbsoluteY);
                    // 按百分比缩放
                    image.scalePercent(logoPercent);
                    document.add(image);
                }
            }
            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            // 字体存放路径
            //fontProvider.register(PDFUtil.class.getClassLoader().getResource("pdfFonts/SimSun.ttf").getPath());
            InputStream stream = PDFUtil.class.getClassLoader().getResourceAsStream("pdfFonts/SimSun.ttf");
            File targetFile = new File("SimSun.ttf");
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, targetFile);
            fontProvider.register(targetFile.getAbsolutePath());
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,
                    new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontProvider);
            document.close();
        } catch (Exception e) {
            logger.error("PDFUtil createPdf error ", e);
        } finally {
            try {
                writer.close();
                outputStream.close();
            } catch (IOException e) {
                logger.error("PDFUtil createPdf close error ", e);
            }
        }
        return outputStream;
    }

    private static byte[] getLogoBytes(String filePath) {
        try {
            //获取输入流
            InputStream fis = PDFUtil.class.getResourceAsStream(filePath);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
