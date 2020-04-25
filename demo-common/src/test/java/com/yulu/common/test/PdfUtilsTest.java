package com.yulu.common.test;

import com.google.common.collect.Maps;
import com.yulu.common.util.PDFUtil;

import java.util.Map;

/**
 * Created by Yulu on 2020/4/24.
 */
public class PdfUtilsTest {
    public static void main(String[] args) {
        PDFUtil pdfUtil = new PDFUtil();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("invoiceNo", "666666");
        pdfUtil.createPdf(dataMap, "orderInvoiceTemplate.html", "A4",
                "/pdfTemplates/img/NRI-Logo.jpg", 40, 750, 25);
    }
}
