package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class PDFGenerator {

    @Value("${pdfDir}")
    private String pdfDir;

    public void generatePDF(OutputStream outputStream, TicketEvent ticket) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(ticket.getEventName(), font);

            document.add(chunk);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
