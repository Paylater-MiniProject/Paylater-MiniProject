package com.mandiri.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.services.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFPayLaterMonthlyGenerator {

    @Value("${pdfDir}")
    private String pdfDir;

    @Value("${reportFileName}")
    private String reportFileName;

    @Value("${reportFileNameDateFormat}")
    private String reportFileNameDateFormat;

    @Value("${logoImgPath}")
    private String logoImgPath;

    @Value("${logoImgScale}")
    private Float[] logoImgScale;

    @Value("${table.columnNames}")
    private List<String> columnNames;

    @Autowired
    PaylaterDetailService detailService;

    private static Font COURIER = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);

    public void generatePdfReport(String id) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate(id)));
            document.open();
            addLogo(document);
            addDocTitle(document,id);
            createTable(document,2,id);
            addFooter(document);
            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void addLogo(Document document) {
        try {
            Image img = Image.getInstance(logoImgPath);
            img.scalePercent(logoImgScale[0], logoImgScale[1]);
            img.setAlignment(Element.ALIGN_RIGHT);
            document.add(img);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addDocTitle(Document document,String id) throws DocumentException {

        PaylaterDetailDto payment = detailService.getAllPaymentById(id);

        Paragraph p1 = new Paragraph();
        leaveEmptyLine(p1, 1);
        String title = "LIVIN' PAYLATER";
        p1.add(new Paragraph(title, COURIER));
        /*float titleLineWidth = getTextWidth(title, COURIER);
        float x = (PageSize.A4.getWidth() - titleLineWidth)/2;
        float y = (PageSize.A4.getHeight() - titleLineWidth) / 2;*/
        p1.setAlignment(Element.ALIGN_MIDDLE);
        leaveEmptyLine(p1, 1);

        p1.add(new Paragraph("Total Transaksi: " + payment.getCurrentInstallment() + "/" + payment.getTotalInstallment(), COURIER_SMALL));
        p1.add(new Paragraph("Created Date: " + payment.getCreatedTime(), COURIER_SMALL));
        p1.add(new Paragraph("Due Date: " + payment.getDueDate(), COURIER_SMALL));

        document.add(p1);
    }

    private void createTable(Document document, int noOfColumns,String id) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 3);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(noOfColumns);

        for(int i=0; i<noOfColumns; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        getDbData(table,id);
        document.add(table);
    }

    private void getDbData(PdfPTable table,String id) {

        PaylaterDetailDto payment = detailService.getAllPaymentById(id);

        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBorder(2);

        table.addCell("Total Tagihan");
        table.addCell("+" + payment.getInstallmentPay().toString());

        table.addCell("Total Pembayaran");
        table.addCell("-" + payment.getInstallmentPay().toString());

        table.addCell("Cicilan: " + payment.getProductName().toString());
        table.addCell("+" + payment.getInstallmentPay().toString());

    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph p2 = new Paragraph();
        leaveEmptyLine(p2, 3);
        p2.setAlignment(Element.ALIGN_MIDDLE);
        p2.add(new Paragraph(
                "------------------------End Of " +reportFileName+"------------------------",
                COURIER_SMALL_FOOTER));

        document.add(p2);
    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String getPdfNameWithDate(String id) {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
        return pdfDir+reportFileName+"-"+localDateString+ "-" + id + ".pdf";
    }

    public float getTextWidth(String text, Font font) throws IOException {
        return font.getBaseFont().getWidth(text)/1000 * font.getSize();
    }
}
