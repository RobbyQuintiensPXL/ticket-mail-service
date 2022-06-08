package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class PDFGenerator {

    private final static String[] HEADERS = new String[]{"Title", "Date", "# Tickets", "Price/tickets",
            "Total Amount"};
    @Value("${pdfDir}")
    private String pdfDir;

    public void generatePDF(OutputStream outputStream, TicketEvent ticket) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
            Font subTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 17, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            document.add(new Paragraph("\n\n"));
            Chunk chunkTitle = new Chunk(getTitle(ticket), title);
            document.add(chunkTitle);
            document.add(new Paragraph("\n"));
            Chunk chuckDate = new Chunk(ticket.getEventDate() + " " + ticket.getEventTime(), subTitle);
            document.add(chuckDate);
            document.add(new Paragraph("\n\n"));

            Chunk chunkUser = new Chunk(getUserInfo(ticket), font);
            document.add(chunkUser);
            document.add(new Paragraph("\n"));

            Chunk chunkEmail = new Chunk(ticket.getEmail(), font);
            document.add(chunkEmail);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(HEADERS.length);
            for (String header : HEADERS) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.2f);
                cell.setPhrase(new Phrase(header, headerFont));
                table.addCell(cell);
            }
            table.completeRow();

            PdfPCell ticketTitle = new PdfPCell(new Paragraph(ticket.getEventName()));
            PdfPCell ticketDate = new PdfPCell(new Paragraph(ticket.getEventDate() + " " + ticket.getEventTime()));
            PdfPCell ticketAmount = new PdfPCell(new Paragraph(String.valueOf(ticket.getAmount())));
            PdfPCell ticketPrice = new PdfPCell(new Paragraph(String.valueOf(ticket.getPrice())));
            PdfPCell totalPrice = new PdfPCell(new Paragraph(String.valueOf(ticket.getPrice() * ticket.getAmount())));

            table.addCell(ticketTitle);
            table.addCell(ticketDate);
            table.addCell(ticketAmount);
            table.addCell(ticketPrice);
            table.addCell(totalPrice);
            table.completeRow();

            document.add(table);

            for (int i = 0; i < ticket.getAmount(); i++) {
                document.newPage();
                document.add(chunkTitle);
                document.add(new Paragraph("\n"));
                document.add(chuckDate);
                document.add(new Paragraph("\n\n"));
                int ticketNumer = ticket.getTicketId() + i;
                document.add(new Paragraph("Ticket number: " + ticketNumer));
                Image chucnkQR = getQR(ticket, i);
                document.add(chucnkQR);
            }

            document.addTitle("Ticket for " + ticket.getEventName());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public String getTitle(TicketEvent ticket) {
        return ticket.getEventName() + "\n" + ticket.getBuildingName();
    }

    public String getUserInfo(TicketEvent ticket) {
        return ticket.getName() + " " + ticket.getFirstName();
    }

    public Image getQR(TicketEvent ticketEvent, int ticket) throws BadElementException, IOException {
        String validate = "http://localhost:9080/ticket/tickets/validate/";

        byte[] image = new byte[0];
        try {

            image = QRGenerator.getQRCodeImage(validate + ticketEvent.getEventId() + "/" + ticketEvent.getTicketId() + ticket + "/" + ticketEvent.getTicketUserId(), 250, 250);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return Image.getInstance(image);
    }

}
