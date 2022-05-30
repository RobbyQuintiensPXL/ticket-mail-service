package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerator {

    @Value("${pdfDir}")
    private String pdfDir;

    public void generatePDF(TicketEvent ticket) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfDir + ticket.getEmail() + "-" + ticket.getEventName()));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(ticket.getEventName(), font);

            document.add(chunk);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addDocTitle(Document document, String event) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(event);
        document.add(paragraph);
    }

}
