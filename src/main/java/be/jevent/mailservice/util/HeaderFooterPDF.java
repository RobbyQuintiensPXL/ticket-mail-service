package be.jevent.mailservice.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

public class HeaderFooterPDF extends PdfPageEventHelper {

    public void onEndPage(PdfWriter writer, Document document) {
        Image image;
        try {
            File resource = new ClassPathResource("pdf-template/logo.jpeg").getFile();
            String path = resource.getPath();
            image = Image.getInstance(path);
            image.setAlignment(Element.ALIGN_RIGHT);
            image.setAbsolutePosition(450, 700);
            image.scalePercent(50f, 50f);
            writer.getDirectContent().addImage(image, true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("JEvents"), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
    }
}
