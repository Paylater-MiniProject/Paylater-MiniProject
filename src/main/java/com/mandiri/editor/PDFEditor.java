package com.mandiri.editor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import com.mandiri.blobExample.entity.FileUploader;
import com.mandiri.blobExample.service.FileUploaderService;
import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.models.User;
import com.mandiri.generator.PDFGenerator;
import com.mandiri.services.PaylaterDetailService;
import com.mandiri.services.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PDFEditor {

    @Value("${pdfDir}")
    private String pdfDir;

    @Autowired
    PaylaterDetailService paylaterDetailService;

    @Autowired
    FileUploaderService fileUploaderService;

    @Autowired
    PDFGenerator pdfGenerator;
    public PDFEditor(){
    }

    @Autowired
    UserServiceImpl userService;

    public void editPdf(String fileName,String userId,String id) throws IOException {
        PaylaterDetailDto detailDto = paylaterDetailService.getAllPaymentById(id);
/*        Optional<FileUploader> fileEntityOptional = fileUploaderService.getFile(fileName);
        FileUploader file = fileEntityOptional.get();*/

        User user = userService.getUserById(userId);
        PDFont font = PDType1Font.TIMES_ROMAN;


        PDDocument document = null;
        document = PDDocument.load(new File(pdfDir+fileName+".pdf"));
        document = replaceText(document, "Name", "Name              : " + user.getName());
        document = replaceText(document, "NIK", "NIK              : " + user.getNik());
        document = replaceText(document, "Address", "Address           : "+ user.getAddress());
        document = replaceText(document, "Transaction id", "TransactionId     : "+ id);
        tableAddCell(document,1000, font);
        document.save("D:/"+fileName+"-modify.pdf");
        document.close();
    }

    private static PDDocument replaceText(PDDocument document, String searchString, String replacement) throws IOException {

        if (StringUtils.isEmpty(searchString) || StringUtils.isEmpty(replacement)) {
            return document;
        }

        for (PDPage page : document.getPages()) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List<?> tokens = parser.getTokens();

            searchReplacement(searchString, replacement, tokens);

            PDStream updatedStream = new PDStream(document);
            OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            out.close();
            page.setContents(updatedStream);
        }

        return document;
    }

    private static void searchReplacement(String searchString, String replacement, List<?> tokens) {

        for (int j = 0; j < tokens.size(); j++) {
            Object next = tokens.get(j);
            if (next instanceof Operator) {
                Operator op = (Operator) next;

                String pstring = "";
                int prej = 0;

                if (op.getName().equals("Tj")) {
                    COSString previous = (COSString) tokens.get(j - 1);
                    System.out.println(previous);
                    String string = previous.getString();
                    System.out.println(string);
                    string = string.replaceFirst(searchString, replacement);
                    previous.setValue(string.getBytes());

                } else if (op.getName().equals("TJ")) {
                    COSArray previous = (COSArray) tokens.get(j - 1);
                    for (int k = 0; k < previous.size(); k++) {
                        Object arrElement = previous.getObject(k);
                        if (arrElement instanceof COSString) {
                            COSString cosString = (COSString) arrElement;
                            String string = cosString.getString();

                            if (j == prej) {
                                pstring += string;
                            } else {
                                prej = j;
                                pstring = string;
                            }
                        }
                    }

                    if (searchString.equals(pstring.trim())) {
                        COSString cosString2 = (COSString) previous.getObject(0);
                        cosString2.setValue(replacement.getBytes());

                        int total = previous.size() - 1;
                        for (int k = total; k > 0; k--) {
                            previous.remove(k);
                        }
                    }
                }
            }
        }
    }

    private void tableAddCell(PDDocument document, int pageHeight, PDFont font) throws IOException {

        TableClass tableClass = new TableClass(document, new PDPageContentStream(document, new PDPage(PDRectangle.A4)));

        int[] cellWidths = {250, 290};
        tableClass.setTable(cellWidths, 30, 25, pageHeight - 350);
        tableClass.setTableFont(font, 16, Color.BLACK);

        Color tableHeadColor = new Color(255, 204, 0);
        Color tableBodyColor = new Color(219, 218, 198);

        tableClass.addCell("Total Tagihan", tableHeadColor);
        tableClass.addCell("+Rp51.671", tableBodyColor);

        tableClass.addCell("Total Pengembalian Dana", tableHeadColor);
        tableClass.addCell("Rp0", tableBodyColor);

        tableClass.addCell("Total Pembayaran", tableHeadColor);
        tableClass.addCell("-Rp51.671", tableBodyColor);

        tableClass.addCell("Cicilan", null);
        tableClass.addCell("", null);

        tableClass.addCell("[Promo] Paket Simpati", tableHeadColor);
        tableClass.addCell("+Rp51.671", tableBodyColor);


    }
}
