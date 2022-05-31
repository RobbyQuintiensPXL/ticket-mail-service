package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            document.add(new Paragraph("\n\n"));
            Chunk chunkTitle = new Chunk(getTitle(ticket), title);
            document.add(chunkTitle);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));

            Chunk chunkUser = new Chunk(getUserInfo(ticket), font);
            document.add(chunkUser);
            document.add(new Paragraph("\n"));

            Chunk chunkEmail = new Chunk(ticket.getEmail(), font);
            document.add(chunkEmail);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));

            PdfPTable ticketInfo = new PdfPTable(4);
            PdfPCell ticketTitle = new PdfPCell(new Paragraph(ticket.getEventName()));
            PdfPCell ticketDate = new PdfPCell(new Paragraph(ticket.getEventDate() + " " + ticket.getEventTime()));
            //PdfPCell ticketAmount = new PdfPCell(new Paragraph(ticket.getAmountOfTickets()));
            PdfPCell ticketAmount = new PdfPCell(new Paragraph("4"));
            PdfPCell ticketPrice = new PdfPCell(new Paragraph(String.valueOf(ticket.getPrice())));

            ticketInfo.addCell(ticketTitle);
            ticketInfo.addCell(ticketDate);
            ticketInfo.addCell(ticketAmount);
            ticketInfo.addCell(ticketPrice);

            document.add(ticketInfo);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(TicketEvent ticket) {
        return ticket.getEventName() + " " + ticket.getEventDate();
    }

    public String getUserInfo(TicketEvent ticket) {
        return ticket.getName() + " " + ticket.getFirstName();
    }


}
