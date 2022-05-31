package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class MailService {

    private final static String LOGO = "https://github.com/RobbyQuintiensPXL/ticketing-system/blob/9a8856f76bb48eae58659c8c8adf41a36a2371ad/back-end/mail-service/pdf/logo.jpeg";

    private final JavaMailSender javaMailSender;

    private final PDFGenerator pdfGenerator;

    public MailService(JavaMailSender javaMailSender, PDFGenerator pdfGenerator) {
        this.javaMailSender = javaMailSender;
        this.pdfGenerator = pdfGenerator;
    }

    public void sendEmail(TicketEvent ticketEvent) throws MessagingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        pdfGenerator.generatePDF(outputStream, ticketEvent);

        byte[] bytes = outputStream.toByteArray();
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(ticketEvent.getEmail());
        helper.setSubject("Tickets for: " + ticketEvent.getEventName());
        helper.setText("Hi " + ticketEvent.getFirstName() + " " + ticketEvent.getName() + ",\n\n" +
                "Attached you can find your tickets.\nHave fun! \n\n Best regards,\nJEvents Team");
        helper.addAttachment("ticket.pdf", dataSource);
        helper.addInline("logo", getResource());

        javaMailSender.send(message);
    }

    public Resource getResource() {
        return new FileSystemResource(new File(LOGO));
    }

}
