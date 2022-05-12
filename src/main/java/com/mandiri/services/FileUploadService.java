package com.mandiri.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.mandiri.entities.models.FileUpload;
import com.mandiri.repositories.FileUploadRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {
    @Value("${upload.image.Foldernya}")
    private String pathFile;

    @Autowired
    FileUploadRepository fileUploadRepository;

    public void saveFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        FileUpload file = new FileUpload();
        List<String> allowedExtension = new ArrayList<>();
        allowedExtension.add("pdf");
        allowedExtension.add("png");

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        if (!allowedExtension.contains(extension)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Extension cannot upload");
        }

        File filePhysical = new File(pathFile + fileName+"."+extension);
        multipartFile.transferTo(filePhysical);

        file.setFileName(fileName);
        file.setFileExtension(extension);

        fileUploadRepository.save(file);
    }

    public void modifyFilePdf(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("This a Title", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.close();
    }

}
