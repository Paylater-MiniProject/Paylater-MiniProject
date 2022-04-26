package com.mandiri.generator;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.models.Employee;
import com.mandiri.entities.models.PaylaterDetail;
import com.mandiri.repositories.EmployeeRepository;
import com.mandiri.services.EmployeeService;
import com.mandiri.services.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.plaf.synth.ColorType;

@Component
public class PDFGenerator{

    @Value("${pdfDir}")
    private String pdfDir;

    @Value("${reportFileName}")
    private String reportFileName;

    @Value("${reportFileNameDateFormat}")
    private String reportFileNameDateFormat;

    @Value("${logoImgPath}")
    private String logoImgPath;

    @Value("${logoFooter}")
    private String logoFooter;

    @Value("${logoImgScale}")
    private Float[] logoImgScale;

    @Value("${currencySymbol:}")
    private String currencySymbol;

    @Value("${table_noOfColumns}")
    private int noOfColumns;

    @Value("${table.columnNames}")
    private List<String> columnNames;


    @Autowired
    PaylaterDetailService paylaterDetailService;

    private static Font COURIER = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);

    public void generatePdfReport(String id) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
            document.open();
            addLogo(document);
            addDocTitle(document,id);
            createTable(document,noOfColumns,id);
            addFooter(document);
            document.close();
            System.out.println("------------------Your PDF Report is ready!-------------------------");

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

        PaylaterDetailDto paylaterDetail = paylaterDetailService.getAllPayment(id);

        Paragraph p1 = new Paragraph();
        Paragraph title = new Paragraph();
        leaveEmptyLine(p1, 1);


        p1.add(new Paragraph("Name           : " + "Asep Julianta", COURIER_SMALL));
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph("Address        : " + "Jl.Kemanggisan no.90", COURIER_SMALL));
        leaveEmptyLine(p1, 1);
        p1.add(new Paragraph("Transaction id : " + paylaterDetail.getId(), COURIER_SMALL));


        document.add(title);
        document.add(p1);

    }

    private void createTable(Document document, int noOfColumns,String id) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 3);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(noOfColumns);

        for(int i=0; i<noOfColumns; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell);
        }

        table.setHeaderRows(1);
        getDbData(table,id);



        document.add(table);
    }

    private void getDbData(PdfPTable table,String id) {

        PaylaterDetailDto paylaterDetailDto = paylaterDetailService.getAllPayment(id);

        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBorder(2);

        table.addCell("Cicilan");
        table.addCell(String.valueOf(paylaterDetailDto.getCurrentInstallment() + 1));
        table.addCell("Nama Produk");
        table.addCell(paylaterDetailDto.getProductName());
        table.addCell("Status Pembayaran");
        table.addCell(paylaterDetailDto.getStatus());
        table.addCell("Harga Product");
        table.addCell(paylaterDetailDto.getPrice().toString());
        table.addCell("Qty");
        table.addCell(paylaterDetailDto.getTotalProduct().toString());
        table.addCell("Biaya Admin");
        table.addCell(paylaterDetailDto.getHandlingFee().toString());
        table.addCell("Total Pembayaran");
        table.addCell(paylaterDetailDto.getTransactionAmount().toString());


    }

    private void addFooter(Document document) throws DocumentException {
//        Paragraph p2 = new Paragraph();
//        leaveEmptyLine(p2, 3);
//        p2.setAlignment(Element.ALIGN_CENTER);
//        p2.add(new Paragraph("Livin By Mandiri",
//                COURIER_SMALL_FOOTER));
//
//        document.add(p2);

        try {
            Image img = Image.getInstance(logoFooter);
            img.scalePercent(25, logoImgScale[1]);
            img.setAlignment(Element.ALIGN_LEFT);
            document.add(img);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String getPdfNameWithDate() {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
        return pdfDir+reportFileName+"-"+localDateString+".pdf";
    }

}