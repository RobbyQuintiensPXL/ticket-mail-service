package be.jevent.mailservice.service;

import be.jevent.mailservice.dto.TicketEvent;
import com.lowagie.text.DocumentException;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {

    void sendMessageUsingThymeleafTemplate(String to,
                                           String subject,
                                           Map<String, Object> templateModel, TicketEvent ticketEvent)
            throws IOException, MessagingException, javax.mail.MessagingException, DocumentException;
}
