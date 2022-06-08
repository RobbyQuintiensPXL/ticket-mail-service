package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService{

    private static final String NOREPLY_ADDRESS = "jeventstesting@gmail.com";

    @Value("classpath:/logo.jpeg")
    private Resource resourceFile;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final PDFGenerator pdfGenerator;

    public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine,
                            PDFGenerator pdfGenerator) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.pdfGenerator = pdfGenerator;
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
                                                  TicketEvent ticketEvent) throws MessagingException, DocumentException, IOException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process("email.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody, ticketEvent);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody, TicketEvent ticketEvent) throws MessagingException, DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        pdfGenerator.generatePDF(outputStream, ticketEvent);

        byte[] bytes = outputStream.toByteArray();

        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.jpeg", resourceFile);
        helper.addAttachment("ticket.pdf", dataSource);
        emailSender.send(message);
    }
}
